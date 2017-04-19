package com.jiangli.practice.test;

import com.jiangli.practice.eleme.core.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Jiangli
 * @date 2017/4/18 11:11
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext*.xml"})
public class CalcTest {
    private long startTs;

    @Autowired
    @Qualifier("basicDataSource")
    private DataSource dataSource;

    @Autowired
    private Calculator calculator;

    @Before
    public void before() {
        startTs = System.currentTimeMillis();
        System.out.println("----------before-----------");
        System.out.println();
    }

    @After
    public void after() {
        long cost = System.currentTimeMillis() - startTs;
        System.out.println("----------after-----------:cost:" + cost + " ms");
        System.out.println();
    }

    @Test
    public void testCalc() {
        System.out.println(calculator);
        CalcContext context = new CalcContext();

        Cart cart = new Cart();
        context.setCart(cart);
        List<Item> items = new LinkedList<>();
        cart.setItems(items);

        items.add(newItem("千叶豆腐炒肉",16d));
        items.add(newItem("鸡柳",18d));
        items.add(newItem("青椒炒肉",18d));
        items.add(newItem("豆芽",12d));
        items.add(newItem("韭菜炒蛋",15d));
        items.add(newItem("米饭",2d,5));
        items.add(newItem("蒸腊鸭",18d));

        context.setMerchantId(1);
//        context.setExtraMoneyForEachOrder(1d);
        calculator.calc(context);

        List<Solution> solutions = context.getSolutions();
        Stream<Solution> stream = solutions.stream();
        stream.forEach(solution->{
            System.out.println(solution);
            System.out.println(solution.getPrice());
        });
    }

    public Item newItem(String name,Double money) {
        return newItem(name, money,1);
    }
    public Item newItem(String name,Double money,int num) {
        Item item = new Item();
        item.setPackageMoney(1d);
        item.setName(name);
        item.setMoney(money);
        item.setNum(num);
        return item;
    }
}
