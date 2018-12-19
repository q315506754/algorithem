package com.jiangli.db.test;

import com.github.abel533.entity.Example;
import com.jiangli.db.dao.StuMapper;
import com.jiangli.db.dao.UserGenericMapper;
import com.jiangli.db.dao.UserMapper;
import com.jiangli.db.model.Student;
import com.jiangli.db.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Jiangli on 2016/7/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-mybatis.xml"})
public class MyBatisTest {
    @Autowired
    private SqlSessionFactoryBean sqlSessionFactoryBean;

    @Autowired
    private StuMapper stuMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserGenericMapper userGenericMapper;

    @Test
    public void test_generic_mapper() {
        System.out.println(sqlSessionFactoryBean);
        System.out.println(userMapper);

        System.out.println(userMapper.getByUserId(1L));
    }
    @Test
    public void test_example() {
        System.out.println(sqlSessionFactoryBean);
        System.out.println(userGenericMapper);

        System.out.println(userGenericMapper.selectByPrimaryKey(1L));

        Example example = new Example(User.class);
        example.createCriteria().andLike("mobile","%1376%")
                .andEqualTo("isDeleted",0);
        //example.;
        List<User> users = userGenericMapper.selectByExample(example);
        for (User user : users) {
            System.out.println(user);
        }
    }


    @Test
    public void test_mapper() {
        System.out.println(sqlSessionFactoryBean);
        System.out.println(stuMapper);

        Student u=new Student();
        u.setName("诸葛孔明");
        Student x = stuMapper.selectUser(u);
        System.out.println(x);
    }
}
