package com.jiangli.db.transaction;

import com.jiangli.db.spring.TestBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Jiangli
 * @date 2018/6/19 15:16
 */
@Service
public class SpringTranMain implements InitializingBean {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ApplicationContext applicationContext;


    public SpringTranMain() {
        System.out.println("SpringTranMain cons..");
        System.out.println(this);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet" + dataSource);
        System.out.println(this);
    }

    @PostConstruct
    public void func() {
        System.out.println("PostConstruct" + dataSource);
        System.out.println(this);
    }


    //@Autowired
    //private DataSourceTransactionManager transactionManager;

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath*:applicationContext*.xml"});
        System.out.println(context);

        SpringTranMain bean = context.getBean(SpringTranMain.class);
        TestBean testBean = context.getBean(TestBean.class);

        System.out.println("bean:"+bean);
        DataSource dataSource = bean.dataSource;
        System.out.println(dataSource);
        System.out.println(bean.applicationContext);

        //System.out.println(testBean.dataSource);
        //System.out.println(testBean.applicationContext);

        //System.out.println(bean.transactionManager);
        System.out.println("-----");
        //System.out.println(context.getBean("transactionManager"));
        //System.out.println(context.getBean("dataSource"));
        System.out.println(dataSource.getClass());
        System.out.println(testBean);

        //注解
        //testBean.annoUpdate();

        //硬编码
        //testBean.hardCode();

        //aop
        testBean.aopMethod();

    }



    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //public DataSourceTransactionManager getTransactionManager() {
    //    return this.transactionManager;
    //}
    //
    //public void setTransactionManager(DataSourceTransactionManager transactionManager) {
    //    this.transactionManager = transactionManager;
    //}
}
