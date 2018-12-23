package com.jiangli.bytecode.bcel;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.JavaClass;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Jiangli
 * @date 2018/10/29 16:16
 */
public class BCELMain {
    public static void main(String[] args) throws IOException, URISyntaxException {
        //"com/jiangli/bytecode/bcel/TestClass.class"
        //ClassParser cp = new ClassParser("com/jiangli/bytecode/bcel/TestClass.class");
        //ClassParser cp = new ClassParser(new File(BCELMain.class.getResource("TestClass.class").toURI()).getAbsolutePath());
        ClassParser cp = new ClassParser(new File(BCELMain.class.getResource("/com/jiangli/bytecode/bcel/TestClass.class").toURI()).getAbsolutePath());

        System.out.println(ClassLoader.getSystemResource("TestClass.class"));
        System.out.println(ClassLoader.getSystemResource("/com/jiangli/bytecode/bcel/TestClass.class"));
        System.out.println(ClassLoader.getSystemResource("com/jiangli/bytecode/bcel/TestClass.class"));
        System.out.println(BCELMain.class.getResource("/com/jiangli/bytecode/bcel/TestClass.class"));
        System.out.println(BCELMain.class.getResource("/org/springframework/context/ApplicationContext.class"));
        System.out.println(BCELMain.class.getResource("org/springframework/context/ApplicationContext.class"));
        System.out.println(BCELMain.class.getResource("TestClass.class"));
        System.out.println(BCELMain.class.getClassLoader().getResource("TestClass.class"));
        System.out.println(BCELMain.class.getClassLoader().getResource("/com/jiangli/bytecode/bcel/TestClass.class"));

        System.out.println(BCELMain.class.getClassLoader().getResource("com/jiangli/bytecode/bcel/TestClass.class"));
        System.out.println(BCELMain.class.getClassLoader().getResource("org/springframework/context/ApplicationContext.class"));

        System.out.println(BCELMain.class.getClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader());

        JavaClass jc = cp.parse();
        ConstantPool constantPool = jc.getConstantPool(); // Get the constant pool here.
        for (Constant c : constantPool.getConstantPool()) {
            System.out.println(c); // Do what you need to do with all the constants.
            if (c != null) {
                //System.out.println(c.getTag() + " "); // Do what you need to do with all the constants.
            }
        }

        System.out.println(jc.getMinor());
        System.out.println(jc.getMajor());//十进制 52   1.8

    }
}
