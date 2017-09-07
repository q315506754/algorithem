package com.jiangli.commons.chain;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.config.ConfigParser;
import org.apache.commons.chain.impl.CatalogFactoryBase;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Jiangli
 * @date 2017/9/7 18:39
 */
public class CatalogTest {
    public static void main(String[] args) throws Exception {
        //ChainListener

//        Catalog catalog = new CatalogBase();
        ConfigParser parser = new ConfigParser();
        parser.parse(new ClassPathResource("catalog.xml").getURL());

        Catalog catalog = CatalogFactoryBase.getInstance().getCatalog();
        System.out.println(catalog);
    }

}
