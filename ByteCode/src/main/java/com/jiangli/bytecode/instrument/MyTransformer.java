package com.jiangli.bytecode.instrument;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author Jiangli
 * @date 2019/11/29 9:31
 */
public class MyTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            System.out.println("-------agent start---------for:"+className+" "+ classBeingRedefined + (classBeingRedefined!=null?classBeingRedefined.hashCode():""));

            if (!className.contains("ByteDto")) {
                return classfileBuffer;
            }

            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.get("com.jiangli.bytecode.ByteDto");


            CtMethod run = ctClass.getDeclaredMethod("run");
            run.insertBefore("{ " +
                    "System.out.println(\"MyAgentMain javassist before\" + str); " +
                    "}" +
                    "");
            run.insertAfter("{ " +
                    "System.out.println(\"MyAgentMain javassist after\" + str); " +
                    "}");

            //Class aClass = ctClass.toClass();
            //ByteDto no = (ByteDto)aClass.newInstance();

            //no.run();
            System.out.println("-------agent end---------for:"+className);
            byte[] bytes = ctClass.toBytecode();


            /**
             * If a CtClass object is converted into a class file by writeFile(), toClass(), or toBytecode(), Javassist freezes that CtClass object. Further modifications of that CtClass object are not permitted. This is for warning the developers when they attempt to modify a class file that has been already loaded since the JVM does not allow reloading a class.
             *
             * A frozen CtClass can be defrost so that modifications of the class definition will be permitted. For example,
             */
            ctClass.defrost();

            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error:"+e.getMessage());
        }

        System.out.println("return null....");
        return null;
    }
}
