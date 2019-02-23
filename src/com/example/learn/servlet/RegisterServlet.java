package com.example.learn.servlet;

import com.alibaba.fastjson.JSON;
import com.example.learn.entity.base.BaseRespBean;
import com.example.learn.entity.common.InsertRespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class RegisterServlet extends BaseServlet {

    @Override
    public void onDoPost(HttpServletRequest req, HttpServletResponse resp) throws SQLException {

    }

    @Override
    public void onDoGet(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String userId = req.getParameter("userId");
        String userName = req.getParameter("userName");
        String passwd = req.getParameter("passwd");
        String userType = req.getParameter("userType");
        String gender = req.getParameter("gender");
        String birth = req.getParameter("birth");
        String college = req.getParameter("college");
        String email = req.getParameter("email");

        System.out.println("userId: " + userId);
        System.out.println("userName: " + userName);
        System.out.println("passwd: " + passwd);
        System.out.println("userType: " + userType);
        System.out.println("gender: " + gender);
        System.out.println("birth: " + birth);
        System.out.println("college: " + college);
        System.out.println("email: " + email);

        String sql = String.format("INSERT INTO users " +
                        "(userid, username, usertype, birth, gender, college, passwd, email) " +
                        "VALUES " +
                        "(%s, \"%s\", %s, %s, \"%s\", \"%s\", \"%s\", \"%s\")",
                userId, userName, userType, birth, gender, college, passwd, email
        );

        System.out.println(sql);
        BaseRespBean<InsertRespBean> baseRespBean = new BaseRespBean<>();
        InsertRespBean registerRespBean = new InsertRespBean();
        boolean isExecute = statement.execute(sql);

        if (isExecute) {
            registerRespBean.setCode(0);
            registerRespBean.setMsg("注册成功");
            baseRespBean.setCode(200);
            baseRespBean.setMsg("OK");
            baseRespBean.setData(registerRespBean);
        } else {
            registerRespBean.setCode(-1);
            registerRespBean.setMsg("注册失败");
            baseRespBean.setCode(200);
            baseRespBean.setMsg("OK");
            baseRespBean.setData(registerRespBean);
        }

        statement.close();
        connection.close();

        PrintWriter pw = resp.getWriter();
        pw.print(JSON.toJSONString(baseRespBean));

    }
}
