package com.jiangli.practice.test;

import com.jiangli.practice.eleme.dao.DishRespository;
import com.jiangli.practice.eleme.dao.MerchantRespository;
import com.jiangli.practice.eleme.dao.RuleRespository;
import com.jiangli.practice.eleme.model.Dish;
import com.jiangli.practice.eleme.model.Merchant;
import com.jiangli.practice.eleme.model.Rule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/18 11:11
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext*.xml"})
public class JpaTest {
    private long startTs;

    @Autowired
    private DishRespository dishRespository;

    @Autowired
    private MerchantRespository merchantRespository;

    @Autowired
    private RuleRespository ruleRespository;

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
    public void testFindMerchant() {
        System.out.println(merchantRespository);

        Merchant one = merchantRespository.findOne(1);
        System.out.println(one);

        findAll();
    }
    @Test
    public void testFindDish() {
        System.out.println(dishRespository);

        List<Dish> list = dishRespository.findByMerchantId(1);

        printAll(list);
    }
    @Test
    public void testFindRule() {
        System.out.println(ruleRespository);

        List<Rule> list = ruleRespository.findByMerchantIdOrderBySortAsc(1);

        printAll(list);
    }

    private void findAll() {
        Iterable<Merchant> all = merchantRespository.findAll();
        printAll(all);
    }

    private void printAll(Iterable obj) {
        for (Object o : obj) {
            System.out.println(o);
        }
    }

    @Test
    public void testCreate() {
        Merchant one =new Merchant();
        one.setName("aasd");
        merchantRespository.save(one);

        findAll();
    }
}
