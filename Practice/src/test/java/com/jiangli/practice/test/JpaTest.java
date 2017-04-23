package com.jiangli.practice.test;

import com.jiangli.practice.eleme.dao.DishRepository;
import com.jiangli.practice.eleme.dao.MerchantRepository;
import com.jiangli.practice.eleme.dao.RuleRespository;
import com.jiangli.practice.eleme.model.Dish;
import com.jiangli.practice.eleme.model.Merchant;
import com.jiangli.practice.eleme.model.Rule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
    private DishRepository dishRepository;

    @Autowired
    private MerchantRepository merchantRepository;

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
        System.out.println(merchantRepository);

        Merchant one = merchantRepository.findOne(1);
        System.out.println(one);

        findAll();
    }

    @Rollback(false)
    @Transactional
    @Test
    public void testMerchantLikeit() {
        System.out.println(merchantRepository);

        merchantRepository.setLikeit(1,1);

        findAll();
    }

    @Rollback(false)
    @Transactional
    @Test
    public void testDishLikeit() {
        System.out.println(dishRepository);

        dishRepository.setLikeit(1,1);

        List<Dish> dishes = dishRepository.listAll();
        System.out.println(dishes);
    }

    @Rollback(false)
    @Transactional
    @Test
    public void testDishincTimes() {
        System.out.println(dishRepository);

        dishRepository.incTimes(1,1);
    }

    @Rollback(false)
    @Transactional
    @Test
    public void testDishincTimes2() {
        System.out.println(dishRepository);

        dishRepository.incTimes(2,-1);
    }

    @Test
    public void testFindDish() {
        System.out.println(dishRepository);

        List<Dish> list = dishRepository.findByMerchantId(1);

        printAll(list);
    }


    @Test
    public void testFindMerchantByName() {
        System.out.println(merchantRepository);

        List<Merchant> byNameOrderByLikeit = merchantRepository.findByNameOrderByLikeitDesc(null);
        System.out.println(byNameOrderByLikeit);

        byNameOrderByLikeit = merchantRepository.findAllOrderByLikeitDesc();
        System.out.println(byNameOrderByLikeit);

    }

    @Test
    public void testFindMerchantlistAll() {

        List<Merchant> byNameOrderByLikeit = merchantRepository.listAll();
        System.out.println(byNameOrderByLikeit);
        for (Merchant merchant : byNameOrderByLikeit) {
            System.out.println(merchant);
        }
    }
    @Test
    public void testFindMerchantByName2() {
        System.out.println(merchantRepository);

        List<Merchant> byNameOrderByLikeit = merchantRepository.findByNameOrderByLikeitDesc("asd");
        System.out.println(byNameOrderByLikeit);

    }


    @Test
    public void testFindRule() {
        System.out.println(ruleRespository);

        List<Rule> list = ruleRespository.findByMerchantIdOrderBySortAsc(1);

        printAll(list);
    }

    private void findAll() {
        Iterable<Merchant> all = merchantRepository.findAll();
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
        one.setName("aasd22");
        one.setLikeit(-1);
        merchantRepository.save(one);

        findAll();
    }

    @Test
    public void testCreateDish() {
        Dish one =new Dish();
        one.setName("豆芽");
        one.setPackageMoney(1d);
        one.setMerchantId(1);
        one.setMoney(12d);
        dishRepository.save(one);

        findAll();
    }
}
