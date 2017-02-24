package com.jiangli.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/users")
public class MyRestController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    // http://localhost:8080/users/123
    @RequestMapping(value="/{user}", method=RequestMethod.GET)
    public User getUser(@PathVariable Long user) {
        // ...
//        logger.debug("getUser");
        logger.warn("getUser");

        return new User();
    }

    @RequestMapping(value="/{user}/customers", method= RequestMethod.GET)
    List<Customer> getUserCustomers(@PathVariable Long user) {
        // ...
        logger.debug("getUserCustomers");
        return null;
    }

    @RequestMapping(value="/{user}", method=RequestMethod.DELETE)
    public User deleteUser(@PathVariable Long user) {
        // ...
        logger.debug("deleteUser");
        return null;
    }

}