package com.jiangli.common.test;

import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class JSONTest {
    private static Logger logger = LoggerFactory.getLogger(JSONTest.class);

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

}
