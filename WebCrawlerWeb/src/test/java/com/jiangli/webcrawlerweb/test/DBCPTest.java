package com.jiangli.webcrawlerweb.test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by Jiangli on 2016/7/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-dbcp.xml"})
public class DBCPTest {
    @Autowired
    @Qualifier("basicDataSource")
    private DataSource dataSource;

    @Test
    public void func() {
        System.out.println(dataSource);
        try {
            Connection cnnection = dataSource.getConnection();
            System.out.println(cnnection);
            Statement statement = cnnection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Six limit 10 ");

            ResultSetMetaData metaData = resultSet.getMetaData();
            System.out.println(metaData.getClass());

             String[] stringFields = new String[]{"id","title","type","matchKey","createTime"};
            JSONArray arr = new JSONArray();
            while (resultSet.next()) {
                JSONObject obj = new JSONObject();

                for (String stringField : stringFields) {
                    obj.put(stringField, resultSet.getString(stringField));
                }
                obj.put("sid", resultSet.getInt("sid"));
                obj.put("marked", resultSet.getInt("marked"));
                arr.add(obj);
            }

            resultSet.close();

            System.out.println(arr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void func_update() {
        System.out.println(dataSource);
        try {
            Connection cnnection = dataSource.getConnection();
            System.out.println(cnnection);
            PreparedStatement statement = cnnection.prepareStatement("update Six set marked=? where sid=?");
            statement.setInt(1,1);
            statement.setString(2,"2165");

            boolean execute = statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
