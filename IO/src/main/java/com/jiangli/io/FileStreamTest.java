package com.jiangli.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author Jiangli
 * @date 2017/9/2 15:38
 */
public class FileStreamTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("c:\\hello.txt"),"utf8"));
        String s = br.readLine();
        while (s != null) {
            System.out.println(s);
            s = br.readLine();
        }

    }
}
