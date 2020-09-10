package com.jiangli.db.spring;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jiangli
 * @date 2020/7/29 9:45
 */
//@Component
public class UserDao extends JdbcDaoSupport {


    public String query() throws Exception {
        Connection connection = this.getConnection();
        PreparedStatement query = connection.prepareStatement("SELECT * FROM `db_aries_user`.`TBL_USER` WHERE `ID` = 100000000;");
        ResultSet resultSet = query.executeQuery();
        String mobile=null;
        while (resultSet.next()) {
            mobile = resultSet.getString("MOBILE");
        }
        return mobile;
    }

    public void update() throws Exception {
        Connection connection = this.getConnection();
        System.out.println("BEFORE UPDATE,"+ query());

        //connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `db_aries_user`.`TBL_USER` SET `MOBILE` = ? WHERE `ID` = 100000000;");
        SimpleDateFormat sdf = new SimpleDateFormat("dd HH:mm:ss");
        String newmobile = "" + sdf.format(new Date());
        preparedStatement.setString(1, newmobile);
        int i = preparedStatement.executeUpdate();

        System.out.println(i+" update to "+newmobile);
    }
}
