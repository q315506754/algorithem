package com.jiangli.db.transaction;

import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jiangli
 * @date 2020/7/28 14:27
 */
public class JDBCTranBase {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        try {
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?user=root&password=");
            Connection connection = getConnection();

            //int TRANSACTION_NONE             = 0;
            //int TRANSACTION_READ_UNCOMMITTED = 1;
            //int TRANSACTION_READ_COMMITTED   = 2;
            //int TRANSACTION_REPEATABLE_READ  = 4;
            //int TRANSACTION_SERIALIZABLE     = 8;
            System.out.println("getTransactionIsolation:"+connection.getTransactionIsolation());
            System.out.println("getAutoCommit:"+connection.getAutoCommit());
            connection.setAutoCommit(false);

            System.out.println("BEFORE UPDATE,"+ queryMobile(connection));

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `db_aries_user`.`TBL_USER` SET `MOBILE` = ? WHERE `ID` = 100000000;");
            SimpleDateFormat sdf = new SimpleDateFormat("dd HH:mm:ss");
            String newmobile = "" + sdf.format(new Date());
            preparedStatement.setString(1, newmobile);
            int i = preparedStatement.executeUpdate();
            System.out.println(i+" update to "+newmobile);

            Savepoint savepoint = connection.setSavepoint();

            //connection.commit();
            //connection.rollback();

            connection.rollback(savepoint);

            System.out.println("AFTER UPDATE(same connection),"+ queryMobile(connection));
            System.out.println("AFTER UPDATE(other connection),"+ queryMobile(getConnection()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://192.168.222.8:3306?user=root&password=ablejava");
    }

    @Nullable
    private static String queryMobile(Connection connection) throws SQLException {
        PreparedStatement query = connection.prepareStatement("SELECT * FROM `db_aries_user`.`TBL_USER` WHERE `ID` = 100000000;");
        ResultSet resultSet = query.executeQuery();
        String mobile=null;
        while (resultSet.next()) {
            mobile = resultSet.getString("MOBILE");
        }
        return mobile;
    }

}
