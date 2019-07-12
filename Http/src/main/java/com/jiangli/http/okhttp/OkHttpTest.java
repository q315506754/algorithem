package com.jiangli.http.okhttp;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiangli
 * @date 2019/6/13 18:54
 */
public class OkHttpTest {
    public static void main(String[] args) {
        //String url = "https://www.baidu.com/";
        String url = "https://www.google.com/";
        String param = "";

        OkHttpClient client = new OkHttpClient.Builder()
                // 连接池最大空闲连接30，生存周期30秒(空闲30秒后将被释放)
                .connectionPool(new ConnectionPool(30, 30, TimeUnit.SECONDS))
                // 连接超时，10秒，失败时重试，直到10秒仍未连接上，则连接失败
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                // 写超时，向服务端发送数据超时时间
                .writeTimeout(10, TimeUnit.SECONDS)
                // 读超时，从服务端读取数据超时时间
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36")
                //.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param))
                .get()
                .build();

        try {
            Response execute = client.newCall(request).execute();
            System.out.println(execute);

            ResponseBody body = execute.body();
            System.out.println(body);
            System.out.println(body.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
