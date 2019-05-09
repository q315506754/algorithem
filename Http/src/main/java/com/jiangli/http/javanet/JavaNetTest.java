package com.jiangli.http.javanet;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Jiangli
 * @date 2019/1/23 16:49
 */
public class JavaNetTest {
    public static final String url = "https://api.g2s.cn/aries-server/jiankong";

    public static void main(String[] args)  {
        try {
            URLConnection urlConnection = new URL(url).openConnection();
            HttpURLConnection httpurlConnection = (HttpURLConnection) urlConnection;
            urlConnection.connect();

            System.out.println(httpurlConnection.getResponseCode());
            System.out.println(httpurlConnection.getResponseMessage());
            System.out.println(IOUtils.toString(httpurlConnection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
