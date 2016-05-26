package com.jiangli.bytecode.javassist;

import javassist.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Jiangli on 2016/5/26.
 */
public class JavassistMain {

    public static void main(String[] args) {
        try {
            ClassLoader myClassloder = new ClassLoader() {

            };
            ClassPool pool = ClassPool.getDefault();
            System.out.println("before change:"+JavassisA.class+" 's super is :"+JavassisA.class.getSuperclass());

//额外的class路径需要注册到ClassPool中。
//            pool.insertClassPath(new ClassClassPath(this.getClass()));

            CtClass cc = pool.get("com.jiangli.bytecode.javassist.JavassisA");
            cc.setSuperclass(pool.get("com.jiangli.bytecode.javassist.JavassisB"));
//            cc.writeFile();

            CtMethod funcA = cc.getDeclaredMethod("funcA");
            funcA.insertBefore("System.out.println(\"insertBefore1:\"+$1+\" :\"+$2);");
            funcA.insertBefore("System.out.println(\"insertBefore2:\"+$1+\" :\"+$2);");
            funcA.insertAfter("System.out.println(\"insertAfter1:\"+$1+\" :\"+$2);");
            funcA.insertAfter("System.out.println(\"insertAfter2:\"+$1+\" :\"+$2);");

            byte[] b = cc.toBytecode();
//            Class clazz = cc.toClass();
            Class clazz = cc.toClass(myClassloder);
//            Class clazz = cc.toClass(bean.getClass().getClassLoader());

            System.out.println(clazz);
            System.out.println("after change:"+clazz+" 's super is :"+clazz.getSuperclass());
            System.out.println("after change in org classloader:"+JavassisA.class+" 's super is :"+JavassisA.class.getSuperclass());

//            JavassisA aIns = (JavassisA)clazz.newInstance();
//            aIns.funcA(222,"Hahaha");
            Object aIns = clazz.newInstance();
            Method afuncA = aIns.getClass().getDeclaredMethod("funcA",new Class[]{int.class,String.class});
            afuncA.invoke(aIns, new Object[]{222, "Ahahahahahfsds"});


            //如果需要定义一个新类，只需要
            CtClass newCC = pool.makeClass("Point");

            CtMethod mthd = CtNewMethod.make("public Integer getInteger() { return null; }", newCC);
            newCC.addMethod(mthd);

            CtField f = new CtField(CtClass.intType, "i", newCC);
            newCC.addField(f);

            Class newCCCls = newCC.toClass();
            System.out.println(newCCCls);

            System.out.println(Arrays.toString(newCCCls.getDeclaredMethods()));
            System.out.println(Arrays.toString(newCCCls.getDeclaredFields()));
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

//        catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }
}
