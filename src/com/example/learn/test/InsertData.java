package com.example.learn.test;

import java.sql.*;

public class InsertData {
    // JDBC驱动
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    // 新版JDBC驱动
    public static final String NEW_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // 数据库地址
    public static final String DB_URL = "jdbc:mysql://localhost:3306/LearningOnline";
    // 数据库登录用户名
    public static final String DB_USER = "web";
    // 数据库登录密码
    public static final String DB_PASSWD = "123456";

    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;

    public static void main(String[] args) {
        try {
            Class.forName(NEW_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();





            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRes();
            closeSta();
            closeConn();
        }


    }


    private static void closeSta() {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void closeConn() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void closeRes() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
