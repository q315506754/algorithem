package com.jiangli.jni.app.llk;

//方块数据
public class Fangkuai {

    private byte[] b;

    public Fangkuai(byte[] b) {
        this.b = b;
    }

    public byte[] getBytes() {
        return b;
    }

    public boolean same(Fangkuai fk) {
        byte[] a = fk.getBytes();
        for (int i = 0; i < b.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
}
