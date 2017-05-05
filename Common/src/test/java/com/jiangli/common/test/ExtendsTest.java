package com.jiangli.common.test;

/**
 * @author Jiangli
 * @date 2017/5/4 17:06
 */
public class ExtendsTest {

    static class A {
        public final String str;
        public final Type t=new Type();

        public A(String a) {
            this.str = a;
        }
    }

    static class Type {

        @Override
        public String toString() {
            return null;
        }
    }

    static class B extends A{
        public String str;

        public B(String a) {
            super(a);

            this.str="bill";
//            super.str="hello*";
        }
    }

    public static void main(String[] args) {
        A a  = new B("hello A");
        System.out.println(a.str);
        System.out.println(a.t);

        B b  = new B("hello B");
        System.out.println(b.str);

    }


}
