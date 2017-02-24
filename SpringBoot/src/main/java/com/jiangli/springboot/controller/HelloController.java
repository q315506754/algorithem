package com.jiangli.springboot.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    // http://localhost:8080/hello
    @RequestMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}