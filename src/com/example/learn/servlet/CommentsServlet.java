package com.example.learn.servlet;

import com.alibaba.fastjson.JSON;
import com.example.learn.entity.base.BaseRespBean;
import com.example.learn.entity.common.InsertRespBean;
import com.example.learn.entity.teacher.CommentBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentsServlet extends BaseServlet {
    @Override
    public void onDoGet(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
        final String operateType = req.getParameter("operateType");
        if ("insert".equals(operateType)) {
            final String teacherId = req.getParameter("teacherId");
            final String studentId = req.getParameter("studentId");
            final String commentDesc = req.getParameter("commentDesc");

            final String sql = "insert into comments " +
                    "(teacherid, studentid, commentdesc) values " +
                    "(" + teacherId + ", " +
                    studentId + ", \"" +
                    commentDesc + "\")";

            boolean isExecute = statement.execute(sql);
            BaseRespBean<InsertRespBean> baseRespBean = new BaseRespBean<>();
            baseRespBean.setCode(200);
            baseRespBean.setMsg("success");
            InsertRespBean insertRespBean = new InsertRespBean();
            if (isExecute) {
                insertRespBean.setCode(0);
                insertRespBean.setMsg("评论成功");
            } else {
                insertRespBean.setCode(-1);
                insertRespBean.setMsg("评论失败");
            }
            baseRespBean.setData(insertRespBean);
            resp.getWriter().print(JSON.toJSONString(baseRespBean));
        } else if ("query".equals(operateType)) {
            final String teacherId = req.getParameter("teacherId");
            final String sql = "SELECT comments.*, users.* FROM comments, users WHERE comments.teacherid=" +
                    teacherId + " and users.userid=comments.studentid";
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            final ArrayList<CommentBean> commentBeans = new ArrayList<>();
            CommentBean commentBean = null;
            while (resultSet.next()) {
                String commentid = resultSet.getString("commentid");
                String teacherid = resultSet.getString("teacherid");
                String studentid = resultSet.getString("studentid");
                String commentdesc = resultSet.getString("commentdesc");
                String username = resultSet.getString("username");

                commentBean = new CommentBean();
                commentBean.setCommentId(commentid);
                commentBean.setTeacherId(teacherid);
                commentBean.setStudentId(studentid);
                commentBean.setCommentDesc(commentdesc);
                commentBean.setUserName(username);
                commentBeans.add(commentBean);
            }

            BaseRespBean<ArrayList<CommentBean>> baseRespBean = new BaseRespBean<>();
            baseRespBean.setCode(200);
            baseRespBean.setMsg("success");
            baseRespBean.setData(commentBeans);
            resp.getWriter().print(JSON.toJSONString(baseRespBean));
        }
    }

    @Override
    public void onDoPost(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {

    }
}
