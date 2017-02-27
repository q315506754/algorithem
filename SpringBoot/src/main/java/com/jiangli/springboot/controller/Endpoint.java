package com.jiangli.springboot.controller;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path("/hellors")
public class Endpoint {


    //The Jersey servlet will be registered and mapped to /* by default. You can change the mapping by adding @ApplicationPath to your ResourceConfig.
    //    http://localhost:8080/hellors
    @GET
    public String message() {
        return "Hello JerseyConfig";
    }

}