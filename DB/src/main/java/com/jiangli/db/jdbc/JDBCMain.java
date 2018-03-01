package com.jiangli.db.jdbc;

import org.apache.commons.beanutils.BeanUtils;

import java.sql.*;

/**
 * @author Jiangli
 * <p>
 * CreatedTime  2016/7/12 0012 16:55
 */
public class JDBCMain {
    /**
     * show databases;
     * use test;
     * show tables;
     * select * from stu;
     * <p>
     * //
     * table
     * CREATE TABLE `database_user` (
     * `ID` varchar(40) NOT NULL default '',
     * `UserID` varchar(40) NOT NULL default '',
     * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     * <p>
     * <p>
     * //cn
     * status;
     * show variables like 'character_set_%';
     * show variables like '%server%';
     * alter database test  character set utf8;
     * <p>
     * set names utf8;
     * ===
     * SET character_set_client='utf8';
     * SET character_set_connection='utf8';
     * SET character_set_results='utf8';
     * <p>
     * <p>
     * set character_set_server=utf8;
     * set character_set_database=utf8;
     * set character_set_system=utf8;
     * set collation_connection='utf8';
     * set collation_database=utf8;
     * set collation_server=utf8;
     * <p>
     * //
     * show create database test;
     * show create table stu;
     * ALTER DATABASE test DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
     * ALTER TABLE stu DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
     * ALTER TABLE stu  CHARACTER SET utf8 COLLATE utf8_general_ci;
     * ALTER TABLE stu CONVERT TO CHARACTER SET utf8;
     * <p>
     * show char set;
     * <p>
     * default-character-set=utf8
     * <p>
     * CREATE TABLE stu(
     * name varchar(40) NOT NULL primary key,
     * age int ,
     * class varchar(40)
     * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     * <p>
     * insert into stu values('诸葛孔明',18,'天才班');
     *
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        try {
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?user=root&password=");
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.222.8:3306?user=root&password=ablejava");
            System.out.println(connection);
            System.out.println(connection.getCatalog());
            DatabaseMetaData metaData = connection.getMetaData();
            String schema = connection.getSchema();
            System.out.println(schema);
            System.out.println(BeanUtils.describe(metaData));
            System.out.println(metaData);
            ResultSet catalogs = metaData.getCatalogs();
            //System.out.println(catalogs);
            while (catalogs.next()) {
                ResultSetMetaData metaData1 = catalogs.getMetaData();
                System.out.println(metaData1);
                System.out.println(BeanUtils.describe(metaData1));
            }
            //System.out.println(metaData.getTables());
            //JDBCExecutor.execute(connection);
            //ResultSet rs = metaData.getTables(DATABASE, null,null,new String[]{"TABLE"});//所有表
            String TBL_NAME = "TBL_OPEN_ADVERTISEMENT";
            String DATABASE = "db_aries_operation";
            ResultSet rs = metaData.getTables(DATABASE, "%", TBL_NAME, null);
            System.out.println(rs);
            rs.next();
            System.out.println(rs.getString("TABLE_NAME"));


            //4. 提取表内的字段的名字和类型
            String columnName;
            String columnType;
            ResultSet colRet = metaData.getColumns(DATABASE, "%", TBL_NAME, "%");
            while (colRet.next()) {
                columnName = colRet.getString("COLUMN_NAME");
                columnType = colRet.getString("TYPE_NAME");
                int datasize = colRet.getInt("COLUMN_SIZE");
                int digits = colRet.getInt("DECIMAL_DIGITS");
                int nullable = colRet.getInt("NULLABLE");
                System.out.println(columnName + " " + columnType + " " + datasize + " " + digits + " " + nullable);
                System.out.println(colRet.getString("REMARKS"));
                System.out.println(colRet.getString("IS_AUTOINCREMENT"));
            }

            //表主键
            ResultSet primaryKeyResultSet = metaData.getPrimaryKeys(DATABASE,null,TBL_NAME);
            primaryKeyResultSet.next();
            System.out.println(primaryKeyResultSet.getString("COLUMN_NAME"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
