package com.example.learn.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public abstract class BaseServlet extends HttpServlet {

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=UTF-8");
        try {
            Class.forName(NEW_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();

            onDoGet(req, resp);

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet();
            closeStatement();
            closeConnection();
        }
    }

    public abstract void onDoGet(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName(NEW_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();

            onDoPost(req, resp);

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet();
            closeStatement();
            closeConnection();
        }
    }

    public abstract void onDoPost(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException;



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

    public void closeResultSet() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
