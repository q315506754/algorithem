package com.jiangli.crawler.test;

import com.jiangli.crawler.webmagic.six.mapper.TableMapper;
import com.jiangli.crawler.webmagic.six.model.TableModel;
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
    private TableMapper tableMapper;

//    ALTER TABLE six DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
//    ALTER TABLE six DEFAULT CHARACTER SET gbk COLLATE gbk_chinese_ci;
    @Test
    public void func() {
        System.out.println(sqlSessionFactoryBean);
        System.out.println(tableMapper);

        TableModel u=new TableModel();
        u.setId("iddd");
        u.setTitle("ttttttttttt");
        u.setUrl("uuuuuuuuuuu");
//        u.setName("诸葛孔明");
        tableMapper.insert(u);
    }

    @Test
    public void func_select() {
        System.out.println(sqlSessionFactoryBean);
        System.out.println(tableMapper);

        String str = tableMapper.selectByTitle("跡美しゅり的新婚生活");
        System.out.println(str);

         str = tableMapper.selectByTitle("跡美しゅり的新婚生活xx");
        System.out.println(str);
    }
}
