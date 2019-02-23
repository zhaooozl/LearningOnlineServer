package com.example.learn.servlet;

import com.alibaba.fastjson.JSON;
import com.example.learn.entity.base.BaseRespBean;
import com.example.learn.entity.common.CommonStatusBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ChangePwdServlet extends BaseServlet {

    @Override
    public void onDoGet(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String userId = req.getParameter("userId");
        String oldPasswd = req.getParameter("oldPasswd");
        String newPasswd = req.getParameter("newPasswd");

        BaseRespBean<CommonStatusBean> respBean = new BaseRespBean<>();
        respBean.setCode(200);
        respBean.setMsg("成功");

        CommonStatusBean commonStatusBean = new CommonStatusBean();
        commonStatusBean.setCode(-1);
        commonStatusBean.setMsg("修改失败");

        final String sqlQuery = "SELECT userid, passwd FROM users WHERE userid=" + userId;
        System.out.println("sqlQuery: " + sqlQuery);

        resultSet = statement.executeQuery(sqlQuery);
        String userid = null;
        String passwd = null;
        while (resultSet.next()) {
            userid = resultSet.getString("userid");
            passwd = resultSet.getString("passwd");
            System.out.println("userid: " + userid);
            System.out.println("passwd: " + passwd);
        }
        resultSet.close();

        if (userId.equals(userid) && oldPasswd.equals(passwd)) {
            final String sqlUpdate = "UPDATE users SET passwd=\"" + newPasswd + "\" WHERE userid=" + userId;
            System.out.println("sqlUpdate: " + sqlUpdate);
            int i = statement.executeUpdate(sqlUpdate);
            System.out.println("update i: " + i);
            if (1 == i) {
                commonStatusBean.setCode(0);
                commonStatusBean.setMsg("修改成功");
            } else {
                commonStatusBean.setCode(-1);
                commonStatusBean.setMsg("修改失败");
            }
        } else {
            commonStatusBean.setCode(-1);
            commonStatusBean.setMsg("修改失败");
        }
        respBean.setData(commonStatusBean);
        resp.getWriter().print(JSON.toJSONString(respBean));
    }

    @Override
    public void onDoPost(HttpServletRequest req, HttpServletResponse resp) throws SQLException {

    }
}
