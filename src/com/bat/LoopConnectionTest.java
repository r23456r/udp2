package com.bat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoopConnectionTest {
 
    private static Connection conn = getConn();
 
    public static void main(String[] args) {
 
        List<ResultSet> actualResultSets = new ArrayList<>();
 
        for (int i = 0; i < 3; i++) {
            actualResultSets.add(getAllCategory(conn));
        }
 
 
        boolean flag = true;
        int i = 0;
        while (true) {
 
            try {
                int index = i++;
                flag = displayResultSet(actualResultSets.get(index%3), index%3);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (!flag) {
                break;
            }
        }
 
    }
 
    private static ResultSet getAllCategory(Connection conn) {
        String sql = "select * from user";
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = conn.prepareStatement(sql);
//            pstmt.setFetchSize(100);
            pstmt.setFetchSize(Integer.MIN_VALUE);
            resultSet = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        finally {
//            if (null!=pstmt) {
//                try {
//                    pstmt.close();//注释掉close方法是因为，一旦pstmt关闭，resultSet也会随之关闭
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        return resultSet;
    }
 
    private static boolean displayResultSet(ResultSet rs, int index) throws SQLException {
        int col = rs.getMetaData().getColumnCount();
        System.out.println("index:" + index + "============================");
        boolean flag = rs.next();
        if (flag) {
            System.out.println(rs.getString("name"));
        }
        return flag;
    }
 
    public static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&serverTimezone=GMT&";
        String username = "root";
        String password = "Test@123";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
 
}