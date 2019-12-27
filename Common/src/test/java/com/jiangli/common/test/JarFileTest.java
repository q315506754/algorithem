package com.jiangli.common.test;

import com.jiangli.common.lib.Ck;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Jiangli
 * @date 2018/6/26 13:27
 */
public class JarFileTest {
    private static void process(JarFile jarFile1, Object obj) {
        JarEntry entry = (JarEntry)obj;
        String name = entry.getName();
        long size = entry.getSize();
        long compressedSize = entry.getCompressedSize();
        //System.out.println(name + "\t" + size + "\t" + compressedSize);
        if (name.endsWith("ApplicationContext.class")) {
            System.out.println("gocha!");
            try {
                InputStream input = jarFile1.getInputStream(entry);
                process(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static void process(InputStream input)
            throws IOException {
        InputStreamReader isr =
                new InputStreamReader(input);
        BufferedReader reader = new BufferedReader(isr);
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    @Test
    public void test_() throws Exception {
        JarFile jarFile1 = new JarFile("C:\\Portable\\zhihuishu\\m2repos\\org\\springframework\\spring-context\\4.1.5.RELEASE\\spring-context-4.1.5.RELEASE.jar");
        System.out.println(jarFile1);
        Enumeration enu = jarFile1.entries();
        //while (enu.hasMoreElements()) {
        //    //process(jarFile1,enu.nextElement());
        //}

        //URL url = ClassLoader.getSystemResource("");
//　　或者
//　　　InputStream stream =ClassLoader.getSystemResourceAsStream(name);
//        String name = "com/jiangli/common/lib/Ck.class";
        String name = "org/springframework/context/ApplicationContext.class";
        //String name = "com/jiangli/common/test/JarFileTest.class";
        URL systemResource = ClassLoader.getSystemResource(name);


        System.out.println(systemResource);
        System.out.println(systemResource.getFile());
        System.out.println(systemResource.getProtocol());
        System.out.println(systemResource.getUserInfo());
        JarFile jarFile = new JarFile(systemResource.toString());
        //JarFile jarFile = new JarFile(ClassLoader.getSystemResource("com/jiangli/common/test/JarFileTest.class").getFile());
        System.out.println(jarFile);

    }


    @Test
    public void test_2() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            Enumeration<URL> e = Thread.currentThread().getContextClassLoader().getResources("META-INF/spring.schemas");
            while (e.hasMoreElements()) {
                URL url = e.nextElement();
                System.out.println(url);
            }
        }
    }
    @Test
    public void test_2_1() throws IOException {
        //Enumeration<URL> e =  ClassLoader.getSystemResources("META-INF/spring.schemas");
        Enumeration<URL> e =  Thread.currentThread().getContextClassLoader().getSystemResources("META-INF/spring.schemas");
        while (e.hasMoreElements()) {
            URL url = e.nextElement();
            System.out.println(url);
        }
    }

    @Test
    public void test_2_2() throws IOException {
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(Thread.class.getClassLoader());
        System.out.println(Ck.class.getClassLoader());
        System.out.println(Ck.class.getResource("Describer.class"));
        System.out.println(Ck.class.getClassLoader().getResource("Describer.class"));
        //System.out.println(Ck.class.getResource("/Describer.class"));
        System.out.println(Ck.class.getResource("/com/jiangli/common/lib/Describer.class"));
    }

    @Test
    public void test_3() throws IOException {
        InputStream in = null;
        //String name = "META-INF/spring.schemas";
        //String name = "org/springframework/context/ApplicationContext.class";
        //String name = "com/jiangli/common/test/JarFileTest.class";
        //String name = "com.jiangli.common.test/JarFileTest";
        //String name = "com.jiangli.common.lib/Ck";
        //String name = "com.jiangli.common.lib/Ck.class";
        //String name = "com/jiangli/common/lib/Ck.class";
        String name = "Describer.class";//ok relative
        in = Ck.class.getResourceAsStream(name);
        System.out.println(in);
        if (in != null) {
            readFromInputStream(new HashSet<>(), in);
        }
    }

    @Test
    public void test_3_2() throws IOException {
        InputStream in = null;
        String name = "/com/jiangli/common/lib/Ck.class"; //ok absolute
        in = Ck.class.getResourceAsStream(name);
        System.out.println(in);
        if (in != null) {
            readFromInputStream(new HashSet<>(), in);
        }
    }

    @Test
    public void test_4() {
        URL systemResource = ClassLoader.getSystemResource("");
        System.out.println(systemResource);
        URL systemResource2 = ClassLoader.getSystemResource("/");
        System.out.println(systemResource2);

        String name = "com/jiangli/common/lib/Ck.class";//ok
        //String name = "/com/jiangli/common/lib/Ck.class";//null
        //String name =  "com.jiangli.common.lib/Ck.class";
        URL u2 = ClassLoader.getSystemResource(name);
        System.out.println(u2);
    }



    private static void readFromInputStream(Set<String> names, InputStream in) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            for (;;) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                line = line.trim();
                if (line.length() > 0) {
                    line = line.toLowerCase();
                    names.add(line);
                }
                System.out.println(line);
            }
        } finally {

        }
    }



}
