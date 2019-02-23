package com.example.learn.servlet.student;

import com.alibaba.fastjson.JSON;
import com.example.learn.entity.base.BaseRespBean;
import com.example.learn.entity.student.SubjectRespBean;
import com.example.learn.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class SubjectServlet extends BaseServlet {

    @Override
    public void onDoGet(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
        String operateType = req.getParameter("operateType");
        System.out.println("SubjectServlet onDoGet===========operateType: " + operateType);
        if ("query".equals(operateType)) {
            String userId = req.getParameter("userId");
            System.out.println("userId: " + userId);
            String sqlQuery = null;
            if (userId != null && !"".equals(userId)) {
                sqlQuery = "SELECT subjectid, subjectname, subjectdesc, courseware, userid FROM subject WHERE userid=" + userId;
            } else {
                sqlQuery = "SELECT subjectid, subjectname, subjectdesc, courseware, userid FROM subject";
            }
            System.out.println("sqlQuery: " + sqlQuery);
            ArrayList<SubjectRespBean> subjectBeans = new ArrayList<>();
            BaseRespBean<ArrayList<SubjectRespBean>> respBean = new BaseRespBean<>();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                int subjectid = resultSet.getInt("subjectid");
                long userid = resultSet.getLong("userid");
                String subjectName = resultSet.getString("subjectname");
                String subjectDesc = resultSet.getString("subjectdesc");
                String courseware = resultSet.getString("courseware");

                subjectBeans.add(new SubjectRespBean(subjectid, userid, subjectName, subjectDesc, courseware));
            }
            respBean.setData(subjectBeans);
            respBean.setMsg("success");
            resp.getWriter().print(JSON.toJSONString(respBean));

        } else if ("upload".equals(operateType)) {
            Part part = req.getPart("file");
            System.out.println("part: " + part);
            String disposition = part.getHeader("content-disposition");
            System.out.println("header: " + disposition);
            String fileName = disposition.substring(disposition.lastIndexOf("=") + 2, disposition.length() - 1);
            String rootPath = req.getServletContext().getRealPath("/courseware");
            System.out.println("rootPath===: " + rootPath);
            part.write(rootPath + File.separator + fileName);

        } else if ("download".equals(operateType)) {
            final String fileName = req.getParameter("fileName");
            System.out.println("fileName: " + fileName);
            final String encodeFileName = URLEncoder.encode(fileName, "utf-8");
            System.out.println("encodeFileName: " + encodeFileName);
            final String filePath = req.getServletContext().getRealPath("/courseware") + File.separator + fileName;
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            resp.setHeader("Content-Disposition","attachment; filename=" + encodeFileName + "");
            ServletOutputStream out = resp.getOutputStream();
            out.write(buffer);
            out.flush();
            out.close();
        }
    }

    @Override
    public void onDoPost(HttpServletRequest req, HttpServletResponse resp) throws SQLException {

    }
}
