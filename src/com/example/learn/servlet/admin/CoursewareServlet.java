package com.example.learn.servlet.admin;

import com.alibaba.fastjson.JSON;
import com.example.learn.entity.admin.CoursewareBean;
import com.example.learn.entity.base.BaseRespBean;
import com.example.learn.servlet.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoursewareServlet extends BaseServlet {


    @Override
    public void onDoGet(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {

        String sql = "SELECT * FROM subject";
        ResultSet resultSet = statement.executeQuery(sql);

        BaseRespBean<ArrayList<CoursewareBean>> respBean = new BaseRespBean<>();
        ArrayList<CoursewareBean> coursewareBeans = new ArrayList<>();
        while (resultSet.next()) {
            String subjectid = resultSet.getString("subjectid");
            String subjectname = resultSet.getString("subjectname");
            String userid = resultSet.getString("userid");
            String courseware = resultSet.getString("courseware");

            CoursewareBean coursewareBean = new CoursewareBean();
            coursewareBean.setSubjectId(subjectid);
            coursewareBean.setSubjectName(subjectname);
            coursewareBean.setUserId(userid);
            coursewareBean.setCourseware(courseware);
            coursewareBeans.add(coursewareBean);
        }
        respBean.setCode(200);
        respBean.setMsg("success");
        respBean.setData(coursewareBeans);
        resp.getWriter().print(JSON.toJSONString(respBean));
    }

    @Override
    public void onDoPost(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {

    }
}
