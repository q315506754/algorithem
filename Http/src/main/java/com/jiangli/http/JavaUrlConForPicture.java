package com.jiangli.http;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

/**
 * Created by Jiangli on 2017/8/13.
 */
public class JavaUrlConForPicture {
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
        String url = "https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=0fc9b8c7261f95cab2f89ae4a87e145b/1c950a7b02087bf49212ea50f1d3572c10dfcf89.jpg";
        File out = new File("C:\\Users\\Public\\Desktop\\xx.jpg");
        URL localURL = new URL(url);

        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;

        httpURLConnection.setRequestProperty("Accept-Charset", "utf8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        int responseCode = httpURLConnection.getResponseCode();
        System.out.println(responseCode);
        String responseMessage = httpURLConnection.getResponseMessage();
        System.out.println(responseMessage);


        String tempLine = null;
        InputStream inputStream = httpURLConnection.getInputStream();


        FileOutputStream outputStream = new FileOutputStream(out);
        IOUtils.copyLarge(inputStream,outputStream);

    }
}
