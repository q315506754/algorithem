package com.jiangli.bytecode.asm;

import org.apache.commons.beanutils.BeanUtils;
import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/5/27 0027 10:32
 */
public class ClassReaderTest {
    public static void main(String[] args) {
        Class<ClassReaderTest> cls = ClassReaderTest.class;
        ClassLoader classLoader = cls.getClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream("com/jiangli/common/core/HelloImpl.class");
        System.out.println(resourceAsStream);
        try {
            ClassReader reader = new ClassReader(resourceAsStream);
            System.out.println(BeanUtils.describe(reader));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
