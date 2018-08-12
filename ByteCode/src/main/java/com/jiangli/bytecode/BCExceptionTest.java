package com.jiangli.bytecode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Jiangli
 * @date 2018/5/29 11:23
 */
public class BCExceptionTest {
    public static void main(String[] args) {
        FileInputStream fileInputStream=null;

        try {
            fileInputStream = new FileInputStream(new File("src/aaa.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream!=null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
