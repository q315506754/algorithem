package com.jiangli.instrument;

/**
 * @author Jiangli
 * @date 2019/1/23 14:55
 */
public class TestInst {
    /**
     * -javaagent:C:\myprojects\algorithem\JMX\target\JMX-1.0-SNAPSHOT.jar
     * @param args
     */
    public static void main(String[] args) {
        TestBean testBean = new TestBean();
        while (true) {
            try {
                Thread.sleep(3000);

                testBean.func();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
