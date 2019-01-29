package com.wrpower.pjc_project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class DMConnect {

    private static final String DRIVER_NAME = "dm.jdbc.driver.DmDriver";
    //数据库连接地址
    private static final String URL = "jdbc:dm://dmdsc:5236";
    //用户名
    private static final String USER_NAME = "SYSDBA";
    //密码
    private static final String PASSWORD = "SYSDBA";

//    @RequestMapping("/test")
//    public String test() {
//        Connection connection = null;
//        try {
//            //加载dm数据库的驱动类
//            Class.forName(DRIVER_NAME);
//            //获取数据库连接
//            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
//            System.out.println(" ***********getConnection(URL, USER_NAME, PASSWORD) ");
//            //mysql查询语句
//            String sql = "select * from dual;";
//            PreparedStatement prst = connection.prepareStatement(sql);
//            //结果集
//            ResultSet rs = prst.executeQuery();
//            while (rs.next()) {
//                System.out.println(" ***********rs.next()) ");
//                rs.getString(1);
//                System.out.println("测试1:" + rs.getString(1));
//            }
//            sql = "select count(*) from pm_new_pjc_project; ";
//            prst = connection.prepareStatement(sql);
//            //结果集
//            rs = prst.executeQuery();
//            while (rs.next()) {
//                System.out.println(" ***********rs.next()) ");
//                rs.getString(1);
//                System.out.println("测试2:" + rs.getString(1));
//            }
//            rs.close();
//            prst.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return "test";
//    }

}
