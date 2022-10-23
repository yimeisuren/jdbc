package com.xiong;

import org.junit.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author xiong
 * @date 2022/10/23
 */
public class JDBCMySQL {
    @Test
    public void jdbcFrameworkTest() {
        //设置一些连接信息
        // 1. url包括三部分: 协议, 子协议 和 子名称
        // 2. 协议为jdbc, http, https, files, ftp等
        // 3. 子协议认为是分支, 例如jdbc中可以操作mysql, oracle, db2等
        // 4. 子名称用来标识唯一路径, 例如 host + port + dbName
        String url = "jdbc:mysql://root@localhost:3306/jdbc";
        // String url = "jdbc:mysql://localhost:3306/jdbc";
        // 报错error: Access denied for user ''@'localhost' (using password: YES)
        Properties info = new Properties();
        info.setProperty("username", "root");
        info.setProperty("password", "root");
        // mysql8.0+版本需要设置时区
        info.setProperty("serverTimezone", "Asia/Shanghai");

        try {
            //Driver是sun公司制定的jdbc规范(接口), 实际操作应该指定具体的数据库驱动
            //new com.mysql.jdbc.Driver()指定mysql数据库的驱动, 表示接下来操作mysql数据库, 但也引入了第三方的api
            //todo: 将直接new第三方的类对象改为通过反射创建
            Driver driver = new com.mysql.cj.jdbc.Driver();

            //连接数据库<=>获取数据库连接对象
            Connection connect = driver.connect(url, info);
            System.out.println(connect);

            // //执行sql<=>获取sql执行对象, 并调用执行方法
            // Statement statement = connect.createStatement();
            // String sql = "";
            // statement.execute(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void getDriverClassByReflectionTest() {
        Properties info = new Properties();
        info.setProperty("username", "root");
        info.setProperty("password", "root");
        info.setProperty("serverTimezone", "Asia/Shanghai");
        String url = "jdbc:mysql://root@localhost:3306/jdbc";

        try {
            Driver driver = (Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            //todo: 使用DriverManager来连接数据库, 便于管理不同的数据库连接
            Connection connect = driver.connect(url, info);
            System.out.println(connect);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void replaceDriverWithDriverManagerTest() {
        String url = "jdbc:mysql://root@localhost:3306/jdbc";
        Properties info = new Properties();
        info.setProperty("username", "root");
        info.setProperty("password", "root");
        info.setProperty("serverTimezone", "Asia/Shanghai");

        try {
            Driver mysqlDriver = (Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            (Driver) Class.forName("")

            DriverManager.registerDriver(mysqlDriver);
            Connection connection = DriverManager.getConnection(url, info);
            System.out.println(connection);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

}
