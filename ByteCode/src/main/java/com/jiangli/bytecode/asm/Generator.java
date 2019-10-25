package com.jiangli.bytecode.asm;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Generator {

    public static void main(String[] args) {
        try {
            ClassReader cr = new ClassReader("com/jiangli/common/core/HelloImpl");
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassAdapter classAdapter = new AddTimeClassAdapter(cw);
            //使给定的访问者访问Java类的ClassReader
            cr.accept(classAdapter, ClassReader.SKIP_DEBUG);


            byte[] data = cw.toByteArray();
            //File file = new File(System.getProperty("user.dir") + "\\WebRoot\\WEB-INF\\classes\\com\\asm5\\C.class");
            File file = new File("C://a//C.class");

            FileOutputStream fout = new FileOutputStream(file);
            fout.write(data);
            fout.close();
            System.out.println("success!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}