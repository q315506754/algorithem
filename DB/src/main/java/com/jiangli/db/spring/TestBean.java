package com.jiangli.db.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jiangli
 * @date 2018/7/11 15:09
 */
@Component
public class TestBean {
    @Autowired
    public DataSource dataSource;

    //@Autowired
    //public PlatformTransactionManager platformTransactionManager;

    //@Autowired
    //public Connection plCon;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UserDao userDao;

    public void hardCode() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    userDao.update();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                throw new NullPointerException();
            }
        });
    }

    public void aopMethod() {
        try {
            userDao.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    @Transactional
    //@Transactional(rollbackFor = Exception.class,propagation= Propagation.REQUIRED)
    public void annoUpdate() {
        try {
            queryMobile(dataSource.getConnection());

            userDao.update();

            //System.out.println(platformTransactionManager);
            //
            //Connection connection = dataSource.getConnection();
            //System.out.println("BEFORE UPDATE,"+ queryMobile(connection));
            //
            ////connection.setAutoCommit(false);
            //
            //PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `db_aries_user`.`TBL_USER` SET `MOBILE` = ? WHERE `ID` = 100000000;");
            //SimpleDateFormat sdf = new SimpleDateFormat("dd HH:mm:ss");
            //String newmobile = "" + sdf.format(new Date());
            //preparedStatement.setString(1, newmobile);
            //int i = preparedStatement.executeUpdate();
            //
            //System.out.println(i+" update to "+newmobile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    @Transactional(propagation = Propagation.NEVER)
    public String queryMobile(Connection connection) throws SQLException {
        PreparedStatement query = connection.prepareStatement("SELECT * FROM `db_aries_user`.`TBL_USER` WHERE `ID` = 100000000;");
        ResultSet resultSet = query.executeQuery();
        String mobile=null;
        while (resultSet.next()) {
            mobile = resultSet.getString("MOBILE");
        }
        return mobile;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TransactionTemplate getTransactionTemplate() {
        return this.transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public UserDao getUserDao() {
        return this.userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
