package com.jiangli.http.okhttp;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiangli
 * @date 2019/1/11 16:37
 */
public class AliDingTest {
    public static void main(String[] args) {
        String url = "https://oapi.dingtalk.com/robot/send?access_token=0b6b86e16210125b46c156e6dadee20b1437bd27a8c248be9165d3fe6e40fdd4";
        String param = "{\n" +
                "    \"feedCard\": {\n" +
                "        \"links\": [\n" +
                "            {\n" +
                "                \"title\": \"时代的火车向前开\", \n" +
                "                \"messageURL\": \"https://mp.weixin.qq.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI\", \n" +
                "                \"picURL\": \"https://www.dingtalk.com/\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"title\": \"时代的火车向前开2\", \n" +
                "                \"messageURL\": \"https://mp.weixin.qq.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI\", \n" +
                "                \"picURL\": \"https://www.dingtalk.com/\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }, \n" +
                "    \"msgtype\": \"feedCard\"\n" +
                "}";

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
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param))
                .build();

        try {
            Response execute = client.newCall(request).execute();
            System.out.println(execute);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
