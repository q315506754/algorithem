package com.jiangli.springboot.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})  -Dspring.autoconfigure.exclude
public class Example {

    @RequestMapping("/")
    String home() {
        return "Hello World!Example";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, "--debug");
//        SpringApplication.run(Example.class, args);
    }

}