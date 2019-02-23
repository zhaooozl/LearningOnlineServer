package com.example.learn.servlet.student;

import com.alibaba.fastjson.JSON;
import com.example.learn.entity.base.BaseRespBean;
import com.example.learn.entity.student.TeacherRespBean;
import com.example.learn.servlet.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherServlet extends BaseServlet {

    @Override
    public void onDoGet(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        final String sqlQuery = "SELECT userid, username FROM users WHERE usertype=1";
        System.out.println("sqlQuery: " + sqlQuery);
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        ArrayList<TeacherRespBean> teacherBeans = new ArrayList<>();
        while (resultSet.next()) {
            long userId = resultSet.getLong("userid");
            String userName = resultSet.getString("username");
            teacherBeans.add(new TeacherRespBean(userId, userName));
        }
        BaseRespBean<ArrayList<TeacherRespBean>> respBean = new BaseRespBean<>();
        respBean.setCode(200);
        respBean.setMsg("success");
        respBean.setData(teacherBeans);
        resp.getWriter().print(JSON.toJSONString(respBean));
    }

    @Override
    public void onDoPost(HttpServletRequest req, HttpServletResponse resp) throws SQLException {

    }
}
