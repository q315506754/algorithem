package com.jiangli.bytecode;

/**
 * @author Jiangli
 * @date 2020/9/8 13:29
 */
public class NOClassDefFoundTest {
    public static void main(String[] args) {
        //NOClassDefFoundTest$NOClassDefFound.class
        NOClassDefFound noClassDefFound = new NOClassDefFound();

        /**
         * Exception in thread "main" java.lang.NoClassDefFoundError: com/jiangli/bytecode/NOClassDefFoundCls
         * 	at com.jiangli.bytecode.NOClassDefFoundTest.main(NOClassDefFoundTest.java:11)
         * Caused by: java.lang.ClassNotFoundException: com.jiangli.bytecode.NOClassDefFoundCls
         * 	at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
         * 	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
         * 	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:335)
         * 	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
         * 	... 1 more
         */
        //手动删除class
        NOClassDefFoundCls noClassDefFoundCls = new NOClassDefFoundCls();
        System.out.println("aaaa");

    //    NOClassDefFoundTest$NOClassDefFoundInner

    }

    static class NOClassDefFound {

    }
    class NOClassDefFoundInner {

    }
}
