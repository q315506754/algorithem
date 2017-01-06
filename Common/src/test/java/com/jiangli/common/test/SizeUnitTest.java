package com.jiangli.common.test;

import com.jiangli.common.utils.RandomUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Jiangli
 * @date 2016/11/10 9:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:NOT_EXIST.xml")
public class SizeUnitTest {
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
   public void func() {
      int size = 23345435;
      int size2 = 0b1101101010;

       int length = 15;
       int repeat = 3;
//       RandomUtil.getRandomOne()

       for (int i = 0; i <= length; i++) {
           for (int j = 0; j < repeat; j++) {
               long randomNum = RandomUtil.getRandomNum(i);
               SizeUnit parse = parse(randomNum);
               System.out.println(randomNum+" -> "+parse.size+""+parse.unit);
           }
       }


   }

   class SizeUnit{
       String size;
       String unit;
   }

    public String[] unitStr = new String[]{"b","Kb","Mb","Gb"};

    public SizeUnit parse(long bytes) {
        SizeUnit ret = new SizeUnit();

         double c = bytes;

        for (int i = 0; i < unitStr.length; i++) {
            if (c/1024<1 || i==unitStr.length-1) {
                ret.unit = unitStr[i];
                BigDecimal bigDecimal = new BigDecimal(c).setScale(1, BigDecimal.ROUND_UP);
                BigDecimal floatPart = bigDecimal.subtract(new BigDecimal(bigDecimal.intValue()));

                if(floatPart.doubleValue() > 0){
                    ret.size = bigDecimal.toString();
                } else {
                    ret.size = bigDecimal.setScale(0, BigDecimal.ROUND_DOWN).toString();
                }

                return ret;
            }

            c/=1024;
        }

        return ret;
    }

}
