package com.jiangli.practice.test;

import com.jiangli.common.core.ThreadCollector;
import com.jiangli.practice.eleme.core.*;
import com.jiangli.practice.eleme.model.Rule;
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

        List<Rule> redEnvelops = new LinkedList<>();
        redEnvelops.add(newRule(110d, 9.9d));
        redEnvelops.add(newRule(20d, 1d));
        context.setRedEnvelope(redEnvelops);

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

    @Test
    public void testRedEnvelop() {
        List<Rule> redEnvelops = new LinkedList<>();
        redEnvelops.add(newRule(110d, 9.9d));
        redEnvelops.add(newRule(20d, 1d));


        ArrangementSupport redEnvelopDistributor = new ArrangementSupport(3,redEnvelops.size());

//        for (int[] redEnvelopIdxForList : redEnvelopDistributor) {
//            int envelopIdx = redEnvelopIdxForList[0];
//            Rule redEnvelop = CollectionUtil.get(context.getRedEnvelope(), envelopIdx);
//        }

    }
    private Rule newRule(double reach, double reduce) {
        Rule rule = new Rule();
        rule.setReach(reach);
        rule.setReduce(reduce);
        return rule;
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

    @Test
    public void testCalc20170420() {
        CalcContext context = new CalcContext();

        Cart cart = new Cart();
        context.setCart(cart);
        List<Item> items = new LinkedList<>();
        cart.setItems(items);

        items.add(newItem("西红柿炒蛋",15d));
        items.add(newItem("韭菜千叶丝",12d));
        items.add(newItem("农家小炒肉",18d));
        items.add(newItem("辣子鸡",18d));
        items.add(newItem("豆芽",12d));
        items.add(newItem("鸡柳",18d));
        items.add(newItem("米饭",2d,4));

        List<Rule> redEnvelops = new LinkedList<>();
        redEnvelops.add(newRule(110d, 9.9d));
        redEnvelops.add(newRule(20d, 1d));
//        context.setRedEnvelope(redEnvelops);

        context.setMaxOrder(6);

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

    @Test
    public void testCalc20170421() {
        CalcContext context = new CalcContext();

        Cart cart = new Cart();
        context.setCart(cart);
        List<Item> items = new LinkedList<>();
        cart.setItems(items);

        items.add(newItem("花菜炒肉",18d));
        items.add(newItem("西红柿炒蛋",15d));
        items.add(newItem("韭菜千叶丝",12d));
        items.add(newItem("酸辣鸡杂",18d));
        items.add(newItem("韭菜炒蛋",15d));
        items.add(newItem("鸡柳",18d));
        items.add(newItem("米饭",2d,5));
        items.add(newItem("时令蔬菜",10d));

        List<Rule> redEnvelops = new LinkedList<>();
        redEnvelops.add(newRule(109d, 9.9d));
        redEnvelops.add(newRule(35d, 4d));
        redEnvelops.add(newRule(20d, 1d));
        context.setRedEnvelope(redEnvelops);

        context.setMaxOrder(6);
//        context.setMaxOrder(3);

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

    @Test
    public void testCancel() {
        CalcContext context = new CalcContext();

        Cart cart = new Cart();
        context.setCart(cart);
        List<Item> items = new LinkedList<>();
        cart.setItems(items);

        items.add(newItem("西红柿炒蛋",15d));
        items.add(newItem("韭菜千叶丝",12d));
        items.add(newItem("农家小炒肉",18d));
        items.add(newItem("辣子鸡",18d));
        items.add(newItem("豆芽",12d));
        items.add(newItem("鸡柳",18d));
        items.add(newItem("米饭",2d,4));

        List<Rule> redEnvelops = new LinkedList<>();
        redEnvelops.add(newRule(110d, 9.9d));
        redEnvelops.add(newRule(20d, 1d));
//        context.setRedEnvelope(redEnvelops);

        context.setMaxOrder(6);

        context.setMerchantId(1);

        String queryId = ThreadCollector.generateQueryId();
        context.setQueryId(queryId);

//        context.setExtraMoneyForEachOrder(1d);

        new Thread(() ->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ThreadCollector.cancelExecute(queryId);
        }).start();

        calculator.calc(context);

        context.getSolutions().stream().forEach(System.out::println);

    }

    @Test
    public void testAjaxQuery() {
        CalcContext context = new CalcContext();

        Cart cart = new Cart();
        context.setCart(cart);
        List<Item> items = new LinkedList<>();
        cart.setItems(items);

        items.add(newItem("西红柿炒蛋",15d));
        items.add(newItem("韭菜千叶丝",12d));
        items.add(newItem("农家小炒肉",18d));
        items.add(newItem("辣子鸡",18d));
        items.add(newItem("豆芽",12d));
        items.add(newItem("鸡柳",18d));
        items.add(newItem("米饭",2d,4));

        List<Rule> redEnvelops = new LinkedList<>();
        redEnvelops.add(newRule(110d, 9.9d));
        redEnvelops.add(newRule(20d, 1d));
//        context.setRedEnvelope(redEnvelops);

        context.setMaxOrder(6);

        context.setMerchantId(1);

        String queryId = ThreadCollector.generateQueryId();
        context.setQueryId(queryId);

//        context.setExtraMoneyForEachOrder(1d);

        new Thread(() ->{
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ThreadCollector.QueryResult query = ThreadCollector.query(queryId);
                System.out.println(query);
            }

        }).start();

        calculator.calc(context);

        context.getSolutions().stream().forEach(System.out::println);

    }
}
