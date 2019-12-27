package com.jiangli.http.feign;

import feign.Feign;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.codec.StringDecoder;

/**
 * @author Jiangli
 * @date 2019/6/14 9:42
 */
public class FeignPractice {
    public static void main(String[] args) {
        TestBd target = Feign.builder().decoder(new StringDecoder()).requestInterceptor(
                template -> {
                    System.out.println(template.url());
                    System.out.println(template.queries());
                    //System.out.println(template.toString());
                    System.out.println(template.bodyTemplate());
                }
        ).target(TestBd.class, "https://www.baidu.com");

        target.get("sdsd");
        target.search("sdsd");

        //System.out.println(target.get("sdsd"));
        //System.out.println("----------------------");
        //System.out.println(target.search("more"));
    }

    @Headers({"Content-Type: application/json","Accept: application/json"})
    interface TestBd{
        @RequestLine("GET /{path}")
        String get(@Param("path") String path);

        @RequestLine("GET /s?wd={wd}")
        String search(@Param("wd") String wd);
    }
}
