package com.example.learn.servlet;

import com.alibaba.fastjson.JSON;
import com.example.learn.entity.base.BaseRespBean;
import com.example.learn.entity.common.LoginRespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class LoginServlet extends BaseServlet {


    @Override
    public void onDoPost(HttpServletRequest req, HttpServletResponse resp) {

    }

    @Override
    public void onDoGet(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String userId = req.getParameter("userId");
        String passwd = req.getParameter("passwd");
        String sql = "SELECT userid, passwd, usertype FROM users WHERE userid=" + userId;
        BaseRespBean<LoginRespBean> baseRespBean = new BaseRespBean<>();
        LoginRespBean loginRespBean = new LoginRespBean();
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String userId2 = resultSet.getString("userId");
            String passwd2 = resultSet.getString("passwd");
            int usertype = resultSet.getInt("usertype");
            System.out.println("userId2: " + userId2);
            System.out.println("passwd2: " + passwd2);
            System.out.println("usertype: " + usertype);

            if (userId2.equals(userId) && passwd2.equals(passwd)) {
                loginRespBean.setLoginCode(0);
                loginRespBean.setUserType(usertype);
                loginRespBean.setLoginMsg("登录成功");
            } else {
                loginRespBean.setLoginCode(1);
                loginRespBean.setUserType(-1);
                loginRespBean.setLoginMsg("用户或密码错误");
            }
            baseRespBean.setCode(200);
            baseRespBean.setMsg("成功");
            baseRespBean.setData(loginRespBean);
        }
        System.out.print(JSON.toJSONString(baseRespBean));
        PrintWriter writer = resp.getWriter();
        writer.println(JSON.toJSONString(baseRespBean));
    }
}
