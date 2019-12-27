package com.jiangli.http.jdk;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;

/**
 * @author Jiangli
 * @date 2019/12/20 9:43
 */
public class HttpServerProviderTest {
    /**
     * http://localhost:8080
     * http://localhost:8080/xx?a=23
     * @param args
     */
    public static void main(String[] args) {
        HttpServerProvider provider = HttpServerProvider.provider();
        try {
            HttpServer localhost = provider.createHttpServer(new InetSocketAddress("localhost", 8080), 10);
            //localhost.setHttpsConfigurator();
            localhost.createContext("/", httpExchange -> {
                System.out.println("req:"+httpExchange);
                URI requestURI = httpExchange.getRequestURI();
                System.out.println("requestURI:"+requestURI);
                System.out.println("query:"+requestURI.getQuery());
                ;
                OutputStream responseBody = httpExchange.getResponseBody();
                byte[] bytes;
                Object a = httpExchange.getAttribute("a");
                if (a != null) {
                    bytes = ("okok...很好" + a).getBytes();
                } else {
                    bytes = ("what?...错误").getBytes();
                }

                Headers headers = httpExchange.getResponseHeaders();
                //不加中文会乱码
                headers.add("Content-Type","text/plain;charset=utf-8");

                //不加请求直接没响应头 浏览器空数据异常
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,bytes.length);

                responseBody.write(bytes);

                //注释也没关系 因为前面定义了内容总大小
                //responseBody.flush();
            });
            localhost.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
