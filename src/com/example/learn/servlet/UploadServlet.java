package com.example.learn.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.learn.config.DBHandler;
import com.example.learn.entity.base.BaseRespBean;
import com.example.learn.entity.common.InsertRespBean;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Collection;

@javax.servlet.annotation.MultipartConfig
public class UploadServlet extends HttpServlet {

    // JDBC驱动
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    // 新版JDBC驱动
    public static final String NEW_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // 数据库地址
    public static final String DB_URL = "jdbc:mysql://localhost:3306/LearningOnline?serverTimezone=GMT";
    // 数据库登录用户名
    public static final String DB_USER = "root";
    // 数据库登录密码
    public static final String DB_PASSWD = "root_123";

    public Connection connection;
    public Statement statement;
    public ResultSet resultSet;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        System.out.println("doPost: ");
        System.out.println("request.getContentType(): " + request.getContentType());

        String userId = request.getParameter("userId");
        String subjectName = request.getParameter("subjectName");
        String subjectDesc = request.getParameter("subjectDesc");
        System.out.println("userId: " + userId);
        System.out.println("subjectName: " + subjectName);
        System.out.println("subjectDesc: " + subjectDesc);

        Part part = request.getPart("file");
        System.out.println("part: " + part);
        // disposition
        String disposition = part.getHeader("content-disposition");
        System.out.println("header: " + disposition);
        String fileName = disposition.substring(disposition.lastIndexOf("=") + 2, disposition.length() - 1);
        // 保存路径
        String filePath = request.getServletContext().getRealPath("/courseware") + File.separator + fileName;
        System.out.println("rootPath===: " + filePath);

        final String sql = "INSERT INTO subject " +
                "(userid, subjectname, subjectdesc, courseware)" +
                " VALUES " +
                "(" + userId + ", \"" +
                subjectName + "\", \"" +
                subjectDesc + "\", \"" +
                fileName + "\")";
        System.out.println("sql: " + sql);
        try {
            Class.forName(NEW_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();
            boolean isExecute = statement.execute(sql);

            BaseRespBean<InsertRespBean> baseRespBean = new BaseRespBean<>();
            baseRespBean.setCode(200);
            baseRespBean.setMsg("success");
            InsertRespBean insertRespBean = new InsertRespBean();
            if (isExecute) {
                insertRespBean.setCode(0);
                insertRespBean.setMsg("上传成功");
            } else {
                insertRespBean.setCode(-1);
                insertRespBean.setMsg("上传失败");
            }
            baseRespBean.setData(insertRespBean);
            response.getWriter().print(JSON.toJSONString(baseRespBean));
            closeStatement();
            closeStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeStatement();
            closeStatement();
        }

        part.write(filePath);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet: ");
//        Part part = request.getPart("file");
//        String header = part.getHeader("content-disposition");
//        System.out.println("header: " + header);
//
//        String fileName = "common.lua";
//        String savePath = ".";
//        part.write(savePath + File.separator + fileName);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.print("上传成功");
        System.out.println("上传成功");
    }


    public void closeStatement() {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void Log(String s) {
        getServletContext().log(s);
    }
}
