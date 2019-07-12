package com.jiangli.http.okhttp;

import okhttp3.*;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiangli
 * @date 2019/6/14 10:46
 */
public class OkHttpPractice {
    public static void main(String[] args) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectionPool(new ConnectionPool(10,10, TimeUnit.SECONDS ))
        .connectTimeout(10,TimeUnit.SECONDS)
        .readTimeout(1,TimeUnit.SECONDS)
        .writeTimeout(1,TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        ;
        OkHttpClient build = builder.build();
        Request request = new Request.Builder()
                //.url("https://www.google.com/")
                .url("https://www.baidu.com/")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36")
                .get()
                .build();

        Call call = build.newCall(request);
        System.out.println(call);

        try {
            Response execute = call.execute();
            System.out.println(execute);

            ResponseBody body = execute.body();
            System.out.println(body);
            //System.out.println(IOUtils.toString(body.byteStream()));
            System.out.println("-------------------");
            System.out.println(IOUtils.toString(body.charStream()));
            //System.out.println(body.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
