package com.jiangli.common.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jiangli
 * @date 2017/7/4 16:45
 */
public class XmlToBeanParserTest<T> {

    private long startTs;

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
    public void test_main() {
//        XmlToBeanParser<ProviderBean> parse = XmlToBeanParser.parse("dubbo:service", "beans-dubbo-provider.xml", ProviderBean.class);
//        List<ProviderBean> providerList = parse.getList();
    }
}
