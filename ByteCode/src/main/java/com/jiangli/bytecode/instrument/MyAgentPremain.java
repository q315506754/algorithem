package com.jiangli.bytecode.instrument;

import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @author Jiangli
 * @date 2019/11/28 17:39
 */
public class MyAgentPremain {
    public static void premain(String args, Instrumentation instrumentation) {
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                try {
                    ClassPool pool = ClassPool.getDefault();
                    CtClass ctClass = pool.get("com.jiangli.bytecode.ByteDto");

                    CtMethod run = ctClass.getDeclaredMethod("run");
                    run.insertBefore("{ " +
                            "System.out.println(\"premain javassist before\" + str); " +
                            "}" +
                            "");
                    run.insertAfter("{ " +
                            "System.out.println(\"premain javassist after\" + str); " +
                            "}");

                    //Class aClass = ctClass.toClass();
                    return ctClass.toBytecode();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return new byte[0];
            }
        });

    }

}
