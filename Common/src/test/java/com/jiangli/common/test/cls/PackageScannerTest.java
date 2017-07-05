package com.jiangli.common.test.cls;

import com.jiangli.common.cls.ClassInfo;
import com.jiangli.common.cls.PackageScanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author Jiangli
 * @date 2017/7/5 15:41
 */
public class PackageScannerTest {
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
        public void test_main() {
            String pkg = "com/jiangli";
            List<ClassInfo> infList = getOpenInterfaces(pkg);
            printScanResults(infList);

            List<ClassInfo> implList = getOpenImplements(pkg);
            printScanResults(implList);
        }

    private void printScanResults(List<ClassInfo> scan) {
        System.out.println(scan);
        for (ClassInfo classInfo : scan) {
//            System.out.println(classInfo);
        }
        System.out.println(scan.size());
    }


    private List<ClassInfo> getOpenImplements(String pkg) {
        return PackageScanner.scan(pkg, (baseDirPackage, relativePackage, currentPackage, fileName) -> currentPackage.contains("impl"), one -> true);
    }

    private List<ClassInfo> getOpenInterfaces(String pkg) {
        return PackageScanner.scan(pkg, (baseDirPackage, relativePackage, currentPackage, fileName) -> true, one -> {
            Class cls = one.getCls();
            return cls.isInterface() && cls.getDeclaredMethods().length>0;
        });
    }
}
