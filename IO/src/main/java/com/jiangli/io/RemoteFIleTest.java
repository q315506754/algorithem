package com.jiangli.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Jiangli
 * @date 2018/11/6 14:34
 */
public class RemoteFIleTest {
    public static void main(String[] args) throws IOException {
        //File file = new File("http://alivideo.g2s.cn/zhs_yanfa_150820/ablecommons/demo/201802/6b79b99f40b14722af5cc90eeb517af6.mp4");
        //System.out.println(file.exists());


        URLConnection urlConnection = new URL("http://alivideo.g2s.cn/zhs_yanfa_150820/ablecommons/demo/201802/6b79b99f40b14722af5cc90eeb517af6.mp4").openConnection();
        //urlConnection.connect();
        InputStream inputStream = urlConnection.getInputStream();
        System.out.println(urlConnection.getContentType());
        System.out.println(inputStream);
    }

}
