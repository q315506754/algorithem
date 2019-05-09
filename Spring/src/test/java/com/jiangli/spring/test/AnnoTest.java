package com.jiangli.spring.test;

import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * @author Jiangli
 * @date 2019/1/3 13:49
 */
public class AnnoTest {

    @Target({ElementType.METHOD,ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Te{
        String value() default "pa";
    }


    @Te("meta-tecd")
    @Target({ElementType.METHOD,ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TeCd{
        String value() default "tecd";
    }

    class Pa implements cls{
        @Te
        public void func() {
            System.out.println("Pa");
        }
    }

    @Te("cls")
    interface cls {
    }

    @TeCd("inf-meta")
    interface inf {
        @Te("inf")
        public void func();
    }

    @TeCd("cd-meta")
    class Cd extends Pa implements inf{

        //@Te
        //@Te("cd")
        //@Override
        public void func() {
            super.func();
        }
    }

    @Test
    public void test_findAnnotation() throws NoSuchMethodException {
        //Method func = Cd.class.getDeclaredMethod("func");//必须重写才能获得
        Method func = Cd.class.getMethod("func");//从父类继承而来，必须为public才不报错

        //优先取自己方法的anno  没有则从自己的接口取
        //否则从父类的方法、接口读取
        Te annotation = AnnotationUtils.findAnnotation(func, Te.class);

        System.out.println(annotation);
        System.out.println(AnnotationUtils.getValue(annotation,"value"));

        //获取返回值不为void的map
        System.out.println(AnnotationUtils.getAnnotationAttributes(annotation));


        //优先取自己类上的的anno  没有则从自己的接口取 再没有则迭代自己的anno(包括meta-anno)
        //否则换成父类的  执行上述查找流程
        Te annotationOfClass = AnnotationUtils.findAnnotation(Cd.class, Te.class);
        System.out.println(annotationOfClass);
    }



}
