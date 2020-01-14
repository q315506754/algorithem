package com.jiangli.db.test;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangli.db.dao.QueryUserDto;
import com.jiangli.db.dao.StuMapper;
import com.jiangli.db.dao.UserGenericMapper;
import com.jiangli.db.dao.UserMapper;
import com.jiangli.db.model.Student;
import com.jiangli.db.model.User;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiangli on 2016/7/14.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-mybatis.xml"})
public class MyBatisTest {
    @Autowired
    private SqlSessionFactoryBean sqlSessionFactoryBean;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    //@Autowired
    //private SqlSession sqlSession;

    @Autowired
    private StuMapper stuMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserGenericMapper userGenericMapper;


    @Test
    public void test_sql_ext() {
        User user = new User();
        user.setIsDeleted(0);
        print(userMapper.testCustomSql(user),"testCustomSql");
        print(userMapper.testProvider(user),"testProvider");
    }

    //https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md
    @Test
    public void test_pageHelper() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //第一种，RowBounds方式的调用
        int pageSize = 6;
        List<User> list = sqlSession.selectList("com.jiangli.db.dao.UserMapper.listAll", 0, new RowBounds(0, pageSize));
        print(list,"第一种，RowBounds方式的调用-单个参数");

        //new Object[]{0,"2=2","1=1"}
        Map<String, Object> pMap = new HashMap<>();
        pMap.put("isDeleted", 0);
        pMap.put("p2", "2=2");
        pMap.put("p3", "1=1");
        List<User> listMulti = sqlSession.selectList("com.jiangli.db.dao.UserMapper.listMultiParameter", pMap, new RowBounds(0, pageSize));
        print(listMulti,"第一种，RowBounds方式的调用-多个参数");


        //第二种，Mapper接口方式的调用，推荐这种使用方式。
        PageHelper.startPage(2, 2);//第2页开始 取2条
        List<User> list2 = userMapper.listAll(0);
        print(list2,"第二种，Mapper接口方式的调用PageHelper，startPage");

        //第三种，Mapper接口方式的调用，推荐这种使用方式。
        PageHelper.offsetPage(5, 2);//跳过前5个 从第6个开始取2条
        List<User> list3 = userMapper.listAll(0);
        print(list3,"第三种，Mapper接口方式的调用PageHelper，offsetPage");



        //第四种，参数方法调用

        //当从 Dto - User 中同时发现了 pageNumKey 和 pageSizeKey 参数，这个方法就会被分页。
        //
        //注意：pageNum 和 pageSize 两个属性同时存在才会触发分页操作，在这个前提下，其他的分页参数才会生效。
        //存在以下 Mapper 接口方法，你不需要在 xml 处理后两个参数
        List<User> list4 = userMapper.listByPageHelper(0,3,2);
        print(list4,"第四种，参数方法调用");

        //第五种，Mapper接口方式的调用,rowBounds，offsetPage
        RowBounds rowBounds = new RowBounds(5,2);
        List<User> list5 = userMapper.listAllRowBounds(0, rowBounds);
        print(list5,"第五种，Mapper接口方式的调用,参数，rowBounds，offsetPage");

        //mapper定义必须没有 @Param("dto")
        //当从 Dto - User 中同时发现了 pageNumKey 和 pageSizeKey 参数，这个方法就会被分页。
        QueryUserDto queryUserDto = new QueryUserDto();
        queryUserDto.setIsDeleted(0);
        queryUserDto.setPageNumKey(6);
        queryUserDto.setPageSizeKey(2);
        List<User> list6 = userMapper.listByDto(queryUserDto);
        print(list6,"第六种，Mapper接口方式的调用,参数，dto，offsetPage");

        //mapper定义必须没有 @Param("dto")
        User userDto = new User();
        userDto.setIsDeleted(0);
        userDto.setPageNumKey(6);
        userDto.setPageSizeKey(2);
        List<User> list7 = userMapper.listByUserDto(userDto);
        print(list7,"第七种，Mapper接口方式的调用,参数，dto，offsetPage");

        /**
         * ps:
         * 1). RowBounds方式的调用  需要配置rowBoundsWithCount
         * 2). PageHelper.startPage 静态方法调用
         * 3). 使用参数方式  需要配置 supportMethodsArguments  params
         */

        //List<User> nonePage = userMapper.listAll(0);
        //print(nonePage,"非分页");
    }



    @Test
    public void test_generic_mapper() {
        System.out.println(sqlSessionFactoryBean);
        System.out.println(userMapper);

        System.out.println(userMapper.getByUserId(-1L));
        System.out.println(userMapper.getByUserId(1L));
    }

    @Test
    public void test_update() {
        User user = new User();
        user.setId(1L);
        user.setName("aaaaa");
        userMapper.update(user);
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
        print(users,"users");
    }

    private void print(List users,String text) {
        System.out.println("-------------"+text+"--------------"+users.getClass());
        int count = 0;
        final int max=20;
        for (Object user : users) {
            System.out.println(user);


            count++;

            if (count==max) {
                System.out.println("---more("+(users.size()-count)+")---");
                break;
            }
        }
        PageInfo pageInfo = new PageInfo(users);
        System.out.println(pageInfo);

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
