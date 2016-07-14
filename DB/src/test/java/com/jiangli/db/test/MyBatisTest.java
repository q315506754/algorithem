package com.jiangli.db.test;

import com.jiangli.db.dao.UserMapper;
import com.jiangli.db.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Jiangli on 2016/7/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-mybatis.xml"})
public class MyBatisTest {
    @Autowired
    private SqlSessionFactoryBean sqlSessionFactoryBean;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void func() {
        System.out.println(sqlSessionFactoryBean);
        System.out.println(userMapper);

        Student u=new Student();
        u.setName("诸葛孔明");
        Student x = userMapper.selectUser(u);
        System.out.println(x);
    }
}
