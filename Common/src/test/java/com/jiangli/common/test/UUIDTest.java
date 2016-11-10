package com.jiangli.common.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @author Jiangli
 * @date 2016/11/10 9:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:NOT_EXIST.xml")
public class UUIDTest {
   @Before
   public void before() {
       System.out.println("before");
       System.out.println();
   }

   @Before
   public void after() {
       System.out.println("after");
       System.out.println();
   }

   @Test
   @Repeat(3)
   public void func() {
       UUID uuid = UUID.randomUUID();
       System.out.println(uuid);
   }
}
