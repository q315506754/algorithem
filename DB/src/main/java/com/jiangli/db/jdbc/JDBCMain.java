package com.jiangli.db.jdbc;

import com.jiangli.db.JDBCExecutor;

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

     default-character-set=utf8

     CREATE TABLE stu(
     name varchar(40) NOT NULL primary key,
     age int ,
     class varchar(40)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    insert into stu values('诸葛孔明',18,'天才班');
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?user=root&password=");
            System.out.println(connection);

            JDBCExecutor.execute(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
