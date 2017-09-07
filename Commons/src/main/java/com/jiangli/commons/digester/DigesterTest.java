package com.jiangli.commons.digester;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.config.ConfigRuleSet;
import org.apache.commons.chain.impl.CatalogBase;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Jiangli
 * @date 2017/9/7 19:10
 */
public class DigesterTest {
    public static void main(String[] args) throws Exception {
        Catalog catalog = new CatalogBase();

        // Prepare our Digester instance
        Digester digester = getDigester();
        digester.clear();
        digester.push(catalog);

        // Parse the configuration document
        digester.parse(new ClassPathResource("catalog.xml").getURL());

        System.out.println(catalog);
    }

    private static Digester getDigester() {
        Digester digester = new Digester();
        digester.setUseContextClassLoader(true);
        digester.setValidating(false);


        RuleSet ruleSet = new ConfigRuleSet();
        digester.setNamespaceAware(ruleSet.getNamespaceURI() != null);
        digester.addRuleSet(ruleSet);
        return digester;
    }




}
