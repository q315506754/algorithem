package com.jiangli.db.test;

import com.jiangli.common.utils.DateUtil;
import com.jiangli.common.utils.RandomUtil;
import com.jiangli.db.JDBCExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/7/15 0015 10:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-dbcp.xml"})
public class JDBCTransactionTest {
    @Autowired
    @Qualifier("basicDataSource")
    private DataSource dataSource;

    @Test
    public void testCommit() {
        System.out.println(dataSource);
        try {
            Connection connection = dataSource.getConnection();

            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("update stu set class=? where name=?");
            preparedStatement.setString(1,"天才班-"+ RandomUtil.getRandomNum(1,1000));
            preparedStatement.setString(2, "诸葛孔明");
            preparedStatement.execute();

//            connection.rollback();
            JDBCExecutor.execute(connection,false);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetTranLevel() {
        System.out.println(dataSource);
        try {
            Connection connection = dataSource.getConnection();
            int transactionIsolation = connection.getTransactionIsolation();
            System.out.println(transactionIsolation);
            System.out.println(Integer.toBinaryString(transactionIsolation));

            System.out.println(Integer.toBinaryString(Connection.TRANSACTION_SERIALIZABLE));
            System.out.println(Integer.toBinaryString(transactionIsolation));


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        @Test
    public void testSetTranLevel() {
        System.out.println(dataSource);
        try {
            Connection connection = dataSource.getConnection();

            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("update stu set class=? where name=?");
            preparedStatement.setString(1,"天才班-"+ RandomUtil.getRandomNum(1,1000));
            preparedStatement.setString(2, "诸葛孔明");
            preparedStatement.execute();

//            connection.rollback();
            JDBCExecutor.execute(connection,false);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testSavePoint() {
        System.out.println(dataSource);
        try {
            Connection connection = dataSource.getConnection();

            //must
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("update stu set class=? where name=?");
            preparedStatement.setString(1,"天才班-"+ RandomUtil.getRandomNum(1,1000));
            preparedStatement.setString(2, "诸葛孔明");
            preparedStatement.execute();

            Savepoint save1 = connection.setSavepoint();

//            connection.rollback();
            JDBCExecutor.execute(connection, false);

            preparedStatement.setString(1,"天才班-"+ RandomUtil.getRandomNum(1,1000));
            preparedStatement.setString(2, "诸葛孔明");
            preparedStatement.execute();
            JDBCExecutor.execute(connection, false);

            connection.rollback(save1);

            JDBCExecutor.execute(connection, false);
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSavePointByName() {
        System.out.println(dataSource);
        try {
            Connection connection = dataSource.getConnection();

            //must
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("update stu set class=? where name=?");
            preparedStatement.setString(1,"天才班-"+ RandomUtil.getRandomNum(1,1000));
            preparedStatement.setString(2, "诸葛孔明");
            preparedStatement.execute();

            Savepoint save1 = connection.setSavepoint("ggg");

//            connection.rollback();
            JDBCExecutor.execute(connection, false);

            preparedStatement.setString(1,"天才班-"+ RandomUtil.getRandomNum(1,1000));
            preparedStatement.setString(2, "诸葛孔明");
            preparedStatement.execute();
            JDBCExecutor.execute(connection, false);

            connection.rollback(save1);

            JDBCExecutor.execute(connection, false);
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
