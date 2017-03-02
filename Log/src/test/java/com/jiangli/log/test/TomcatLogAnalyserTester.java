package com.jiangli.log.test;

import com.jiangli.log.analyser.core.Analyser;
import com.jiangli.log.analyser.core.TomcatAnalyser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * @author Jiangli
 * @date 2017/3/1 9:10
 */
public class TomcatLogAnalyserTester {
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
        System.out.println("----------after-----------:cost:"+cost+" ms");
        System.out.println();
    }
    
    @Test
    public void test_ggg() {
        String path = "C:\\Users\\DELL-13\\Desktop\\t\\222222222222";
        Resource resource = new FileSystemResource(path);
        System.out.println(resource.exists());

        Analyser analyser = new TomcatAnalyser(path+"\\result.txt");
        analyser.analyse(resource);
    }

}
