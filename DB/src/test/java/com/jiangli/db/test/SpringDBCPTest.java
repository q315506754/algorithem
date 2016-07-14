package com.jiangli.db.test;

import com.jiangli.db.JDBCExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Jiangli on 2016/7/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-dbcp.xml"})
public class SpringDBCPTest {
    @Autowired
    @Qualifier("basicDataSource")
    private DataSource dataSource;

    @Test
    public void func() {
        System.out.println(dataSource);
        try {
            Connection cnnection = dataSource.getConnection();
            JDBCExecutor.execute(cnnection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
