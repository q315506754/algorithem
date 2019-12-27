package com.jiangli.http.okhttp;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**

 $.ajax({
 type: "POST",
 url:"https://oapi.dingtalk.com/robot/send?access_token=ba312a0d85bbb8dd63b58efb5496d8ae94e87b48f7ebd6dabf8f722c8cd799d1",
 data: {
 "msgtype": "text",
 "text": {
 "content": "a 阿诗丹顿"
 }
 },
 dataType: "json",
 success: function (response) {

 }
 });


 $.ajax({
 type: "POST",
 url:"http://localhost:8010/user/login",
 data: {
 "msgtype": "text",
 "text": {
 "content": "a 阿诗丹顿"
 }
 },
 dataType: "json",
 success: function (response) {

 }
 });


 $.ajax({
 type: "POST",
 url:"http://localhost:8010/redirectJson?url=https%3A%2F%2Foapi.dingtalk.com%2Frobot%2Fsend%3Faccess_token%3Dba312a0d85bbb8dd63b58efb5496d8ae94e87b48f7ebd6dabf8f722c8cd799d1",
 contentType:"application/json",
 data:JSON.stringify({
     "msgtype": "text",
     "text": {
        "content": "a 阿诗丹顿11"
     }
 }),
 success: function (response) {
    console.log(response)
 }
 });

 * @author Jiangli
 * @date 2019/1/11 16:37
 */
public class AliDingTest22 {
    public static void main(String[] args) {
        String url = "https://oapi.dingtalk.com/robot/send?access_token=ba312a0d85bbb8dd63b58efb5496d8ae94e87b48f7ebd6dabf8f722c8cd799d1";
        String param = "{\n" +
                "    \"msgtype\": \"text\", \n" +
                "    \"text\": {\n" +
                "        \"content\": \"a 阿诗丹顿\"\n" +
                "    }"+
                "}";

        System.out.println(param);

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
