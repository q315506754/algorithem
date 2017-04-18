package com.jiangli.practice.test;

import com.jiangli.common.utils.PathUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

/**
 * @author Jiangli
 * @date 2017/4/18 11:11
 */
public class DerbyTest {
    private long startTs;

    @Before
    public void before() {
        startTs = System.currentTimeMillis();
        System.out.println("----------before-----------");
        System.out.println();
    }

    @After
    public void after() {
        long cost = System.currentTimeMillis() - startTs;
        System.out.println("----------after-----------:cost:" + cost + " ms");
        System.out.println();
    }

    @Test
    public void test_as() {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
//        String url = "jdbc:derby:firstdb;create=true";
        String protocol="jdbc:derby:";
        String suffix=";create=true";
//        String suffix=";";
//        jdbc:derby:d:/temp/test;create=true

        //use Database of Intellij IDE
        //D:\algorithem\Practice\src\main\resources\db
        String dbName= PathUtil.buildPath(PathUtil.getProjectPath("Practice").getProjectBasePath(), false,PathUtil.PATH_SRC_MAIN_RESOURCES,"db");
        String url = protocol+dbName+suffix;
        //jdbc:derby:D:\algorithem\Practice\src\main\resources\db;create=true
        System.out.println(url);

        Connection conn;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url);

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from APP.MERCHANT");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //INSERT INTO APP.MERCHANT(ID, NAME) VALUES(1,'浏阳蒸菜');

    //INSERT INTO APP.DISH(ID, NAME, MONEY, PACKAGE, MERCHANTID) VALUES (1,'米饭',2,1,1)

    //DISH
}
