package com.jiangli.db.jdbc;

import java.sql.*;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/7/12 0012 16:55
 */
public class JDBCMain {
    /**
      show databases;
     use test;
      show tables;
        select * from stu;

//
     table
     CREATE TABLE `database_user` (
     `ID` varchar(40) NOT NULL default '',
     `UserID` varchar(40) NOT NULL default '',
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


     //cn
     status;
     show variables like 'character_set_%';
     show variables like '%server%';
     alter database test  character set utf8;

     set names utf8;
     ===
     SET character_set_client='utf8';
     SET character_set_connection='utf8';
     SET character_set_results='utf8';


     set character_set_server=utf8;
     set character_set_database=utf8;
     set character_set_system=utf8;
     set collation_connection='utf8';
     set collation_database=utf8;
     set collation_server=utf8;

     //
     show create database test;
     show create table stu;
     ALTER DATABASE test DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
     ALTER TABLE stu DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
     ALTER TABLE stu  CHARACTER SET utf8 COLLATE utf8_general_ci;
     ALTER TABLE stu CONVERT TO CHARACTER SET utf8;

     show char set;

    insert into stu values('诸葛孔明',18,'天才班');
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?user=root&password=");
            System.out.println(connection);

            PreparedStatement preparedStatement = connection.prepareStatement("select * from stu;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("--------------");
                String name = resultSet.getString("name");
                String classS = resultSet.getString("class");
                System.out.println(name);
                System.out.println(classS);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
