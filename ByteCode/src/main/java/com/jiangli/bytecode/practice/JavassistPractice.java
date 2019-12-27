package com.jiangli.bytecode.practice;

import com.jiangli.bytecode.ByteDto;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Jiangli
 * @date 2019/11/28 17:30
 */
public class JavassistPractice {
    public static void main(String[] args) {
        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass ctClass = pool.get("com.jiangli.bytecode.ByteDto");

            CtMethod run = ctClass.getDeclaredMethod("run");
            run.insertBefore("{ " +
                    "System.out.println(\"javassist before\" + str); " +
                    "}" +
                    "");
            run.insertAfter("{ " +
                    "System.out.println(\"javassist after\" + str); " +
                    "}");

            Class aClass = ctClass.toClass();
            ByteDto byteDto = (ByteDto) aClass.newInstance();
            byteDto.run();

            File out = new File(ClassLoader.getSystemResource("com/jiangli/bytecode/ByteDto.class").toURI());
            System.out.println(out);
            FileOutputStream fout = new FileOutputStream(out);
            fout.write(ctClass.toBytecode());
            fout.close();
            System.out.println("now generator cc success!!!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
