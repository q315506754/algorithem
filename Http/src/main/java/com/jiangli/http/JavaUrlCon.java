package com.jiangli.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by Jiangli on 2017/8/13.
 */
public class JavaUrlCon {
    private static String proxyHost = null;
    private static Integer proxyPort = null;

    private static URLConnection openConnection(URL localURL) throws IOException {
        URLConnection connection;
        if (proxyHost != null && proxyPort != null) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            connection = localURL.openConnection(proxy);
        } else {
            connection = localURL.openConnection();
        }
        return connection;
    }

    public static void main(String[] args) throws Exception {
//        String url = "https://api.github.com/repos/{owner}/{repo}/contributors";
        String url = "https://api.github.com/repos/OpenFeign/feign/contributors";

        URL localURL = new URL(url);

        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;

        httpURLConnection.setRequestProperty("Accept-Charset", "utf8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        int responseCode = httpURLConnection.getResponseCode();
        System.out.println(responseCode);
        String responseMessage = httpURLConnection.getResponseMessage();
        System.out.println(responseMessage);


        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        InputStream inputStream = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        while ((tempLine = reader.readLine()) != null) {
            resultBuffer.append(tempLine);
        }

        System.out.println(resultBuffer);
    }
}
