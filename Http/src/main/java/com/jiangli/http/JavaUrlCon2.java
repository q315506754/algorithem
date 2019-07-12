package com.jiangli.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by Jiangli on 2017/8/13.
 */
public class JavaUrlCon2 {
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
        String url = "https://auth.cloudv.haplat.net/tokens";

        URL localURL = new URL(url);

        URLConnection connection = openConnection(localURL);

        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", "utf8");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");

        int responseCode = httpURLConnection.getResponseCode();
        System.out.println(responseCode);
        String responseMessage = httpURLConnection.getResponseMessage();
        System.out.println(responseMessage);


        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        InputStream inputStream = httpURLConnection.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        while ((tempLine = reader.readLine()) != null) {
            resultBuffer.append(tempLine);
        }

        System.out.println(resultBuffer);
    }
}
