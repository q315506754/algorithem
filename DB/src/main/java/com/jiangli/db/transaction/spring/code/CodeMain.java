package com.jiangli.db.transaction.spring.code;

import com.jiangli.db.spring.TestBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * @author Jiangli
 * @date 2018/6/19 15:16
 */
@Service
public class CodeMain {

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath*:spring/code.xml"});
        System.out.println(context);

        TestBean testBean = context.getBean(TestBean.class);

        System.out.println("testBean:"+testBean);

        //testBean.updateAndQuery();
        testBean.hardCode();

    }


}
