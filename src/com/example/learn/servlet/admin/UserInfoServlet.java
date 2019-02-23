package com.example.learn.servlet.admin;

import com.alibaba.fastjson.JSON;
import com.example.learn.entity.admin.UserInfoBean;
import com.example.learn.entity.base.BaseRespBean;
import com.example.learn.entity.common.CommonStatusBean;
import com.example.learn.servlet.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserInfoServlet extends BaseServlet {

    @Override
    public void onDoGet(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String operateType = req.getParameter("operateType");

        if ("query".equals(operateType)) {
            String userType = req.getParameter("userType");
            String userId = req.getParameter("userId");
            ArrayList<UserInfoBean> userInfoBeans = null;
            if (userType != null && !"".equals(userType)) {
                userInfoBeans = getUserInfo(1, userType);
            } else {
                userInfoBeans = getUserInfo(2, userId);
            }
            BaseRespBean<ArrayList<UserInfoBean>> respBean = new BaseRespBean<>();
            respBean.setCode(200);
            respBean.setMsg("success");
            respBean.setData(userInfoBeans);
            resp.getWriter().print(JSON.toJSONString(respBean));
        } else if ("delete".equals(operateType)){
            String userId = req.getParameter("userId");
            CommonStatusBean commonStatusBean = deleteUserInfo(userId);
            BaseRespBean<CommonStatusBean> respBean = new BaseRespBean<>();
            respBean.setData(commonStatusBean);
            resp.getWriter().print(JSON.toJSONString(respBean));
        } else if ("update".equals(operateType)) {
            String id = req.getParameter("id");
            String userId = req.getParameter("userId");
            String username = req.getParameter("userName");
            String passwd = req.getParameter("passwd");
            String birth = req.getParameter("birth");
            String college = req.getParameter("college");
            String email = req.getParameter("email");
            String gender = req.getParameter("gender");
            String usertype = req.getParameter("userType");
            CommonStatusBean commonStatusBean = updateUserInfo(id, userId, username, passwd, birth, college, email, gender, usertype);
            BaseRespBean<CommonStatusBean> respBean = new BaseRespBean<>();
            respBean.setData(commonStatusBean);
            resp.getWriter().print(JSON.toJSONString(respBean));
        }

        closeResultSet();
        closeStatement();
        closeConnection();
    }

    private ArrayList<UserInfoBean> getUserInfo(int type, String param) throws SQLException {
        String sql = "";
        if (type == 1) {
            sql = "SELECT * FROM users WHERE usertype=" + param;
        } else {
            sql = "SELECT * FROM users WHERE userid=" + param;
        }
        System.out.print("sql: " + sql);
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<UserInfoBean> userInfoBeans = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String userid = resultSet.getString("userid");
            String username = resultSet.getString("username");
            String usertype = resultSet.getString("usertype");
            String passwd = resultSet.getString("passwd");
            String gender = resultSet.getString("gender");
            String birth = resultSet.getString("birth");
            String college = resultSet.getString("college");
            String email = resultSet.getString("email");

            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.setId(id);
            userInfoBean.setUserId(userid);
            userInfoBean.setUserName(username);
            userInfoBean.setUserType(usertype);
            userInfoBean.setBirth(birth);
            userInfoBean.setGender(gender);
            userInfoBean.setCollege(college);
            userInfoBean.setPasswd(passwd);
            userInfoBean.setEmail(email);
            userInfoBeans.add(userInfoBean);
        }
        return userInfoBeans;
    }

    private CommonStatusBean deleteUserInfo(String userId) throws SQLException {
        final String sql = "DELETE FROM users WHERE userid=" + userId;
        boolean execute = statement.execute(sql);
        CommonStatusBean commonStatusBean = new CommonStatusBean();
        if (!execute) {
            commonStatusBean.setCode(0);
            commonStatusBean.setMsg("删除成功");
        } else {
            commonStatusBean.setCode(-1);
            commonStatusBean.setMsg("删除失败");
        }
        return commonStatusBean;
    }

    private CommonStatusBean updateUserInfo(String id,
                                            String userId,
                                            String userName,
                                            String passwd,
                                            String birth,
                                            String college,
                                            String email,
                                            String gender,
                                            String userType) throws SQLException {
        final String sql = "update users set " +
                "userid=" + userId + ", " +
                "userType=" + userType + ", " +
                "username=\"" + userName + "\"," +
                "passwd=" + passwd + ", " +
                "gender=\"" + gender + "\"," +
                "birth=\"" + birth + "\"," +
                "college=\"" + college + "\"," +
                "email=\"" + email + "\" where id=" + id
                ;

        System.out.print(sql);
        int i = statement.executeUpdate(sql);
        CommonStatusBean commonStatusBean = new CommonStatusBean();
        if (i != 0) {
            commonStatusBean.setCode(0);
            commonStatusBean.setMsg("更新成功");
        } else {
            commonStatusBean.setCode(-1);
            commonStatusBean.setMsg("更新失败");
        }
        return commonStatusBean;
    }

    @Override
    public void onDoPost(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {

    }
}
