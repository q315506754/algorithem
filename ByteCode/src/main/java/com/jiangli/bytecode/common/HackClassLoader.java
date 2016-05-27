package com.jiangli.bytecode.common;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/5/27 0027 14:37
 */
public class HackClassLoader extends ClassLoader{
    public HackClassLoader() {
        super(HackClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classByte) {
        return defineClass(null, classByte, 0, classByte.length);
    }

}
