package com.example.learn.config;

import java.sql.*;

public class DBHandler {

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

    private Connection conn;
    private Statement sta;
    ResultSet resultSet;

    public DBHandler() {
        init();
    }


    public void init() {
        try {
            Class.forName(NEW_JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            sta = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean execute(String sql) {
        if (sta != null && sql != null) {
            try {
                boolean isExecute = sta.execute(sql);
                sta.close();
                conn.close();
                return isExecute;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeSta();
                closeConn();
            }
        }
        return false;
    }


    public ResultSet executeQuery(String sql){
        try {
            resultSet = sta.executeQuery(sql);

//            while (resultSet.next()) {
//                String userid = resultSet.getString("userid");
//                String passwd = resultSet.getString("passwd");
//                System.out.print("userid: " + userid);
//                System.out.print("passwd: " + passwd);
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            closeResultSet();
//            closeStatement();
//            closeConnection();
        }
        return resultSet;
    }



    private void closeSta() {
        try {
            if (sta != null) {
                sta.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConn() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeRes() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class Builder {
        private String sql;

        public void setSql(String sql) {
            this.sql = sql;
        }

        public DBHandler build() {
            return new DBHandler();
        }
    }

}
