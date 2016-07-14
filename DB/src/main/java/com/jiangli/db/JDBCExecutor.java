package com.jiangli.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Jiangli on 2016/7/14.
 */
public class JDBCExecutor  {
    public  static void execute(Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from stu;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("--------------");
                String name = resultSet.getString("name");
                String classS = resultSet.getString("class");
                System.out.println(name);
                System.out.println(classS);

            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
