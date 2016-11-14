package com.jiangli.security.test;

import com.jiangli.common.core.Sorter;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class SecurityTest {
    private static Logger logger = LoggerFactory.getLogger(SecurityTest.class);


    /**
     * @param args
     */
    public static void main(String[] args) {
        Class<Sorter> cls = Sorter.class;
        Package clsPackage = cls.getPackage();
        System.out.println(clsPackage);
        String implementationVersion = clsPackage.getImplementationVersion();
        System.out.println(implementationVersion);

        ProtectionDomain protectionDomain = cls.getProtectionDomain();
        System.out.println(protectionDomain);
        CodeSource codeSource = protectionDomain.getCodeSource();
        System.out.println("codeSource:"+codeSource);
        String file = codeSource.getLocation().getFile();
        System.out.println(file);

//        Package aPackage = Package.getPackage("java.lang");
//        Package aPackage = Package.getPackage("java");
        Package aPackage = Package.getPackage("java.io");
//        Package aPackage = Package.getPackage("sun");
        System.out.println(aPackage);



        try {
            System.out.println(BeanUtils.describe(aPackage));

            Class<?> aClass = Class.forName("org.springframework.beans.factory.FactoryBean");

            System.out.println(aClass);

            ProtectionDomain domain = aClass.getProtectionDomain();
            CodeSource source = domain.getCodeSource();
            System.out.println(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
