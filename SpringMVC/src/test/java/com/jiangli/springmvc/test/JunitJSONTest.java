package com.jiangli.springmvc.test;

import com.jiangli.junit.spring.RepeatFixedTimes;
import com.jiangli.junit.spring.StatisticsSpringJunitRunner;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@RunWith(StatisticsSpringJunitRunner.class)
@ContextConfiguration(locations = {"classpath*:test/applicationContext.xml"})
public class JunitJSONTest {
    private static Logger logger = LoggerFactory.getLogger(JunitJSONTest.class);

    @Test
    public void func() {
        JSONObject json = new JSONObject();
        json.put("a","null");
//        json.put("a","<null>");
        json.put("b",null);
        json.put("c","NULL");
        json.put("d","NuLl");
        json.put("e","Null");
        logger.debug(json.toString());

        Object a = json.get("a");
        logger.debug(a.getClass().toString());
        String aStr = json.getString("a");
        logger.debug(aStr);
        Object c = json.get("c");
        logger.debug(c.getClass().toString());

    }

    @RepeatFixedTimes(10)
    @Test
    public void test_aaa() {
        System.out.println("aaa");
    }


}
