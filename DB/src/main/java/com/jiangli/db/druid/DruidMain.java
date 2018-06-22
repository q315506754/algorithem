package com.jiangli.db.druid;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Jiangli
 * @date 2018/6/19 15:16
 */
@Component
public class DruidMain {
    @Resource
    private DataSource dataSource;


    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath*:applicationContext*.xml"});;
        System.out.println(context);

        DruidMain bean = context.getBean(DruidMain.class);
        System.out.println(bean);
        DataSource dataSource = bean.dataSource;
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
        System.out.println(dataSource.getConnection());
        System.out.println(dataSource.getConnection());
    }

}
