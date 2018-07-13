package com.jiangli.db.spring;

import com.jiangli.common.model.Student;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author Jiangli
 * @date 2018/7/11 14:16
 */
//@Component
public class HelloFactory implements FactoryBean<Student> {
    public HelloFactory() {
        System.out.println("HelloFactory construct!!");
    }

    @Override
    public Student getObject() throws Exception {
        System.out.println("HelloFactory getObject");
        return new Student();
    }

    @Override
    public Class<Student> getObjectType() {
        return Student.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
