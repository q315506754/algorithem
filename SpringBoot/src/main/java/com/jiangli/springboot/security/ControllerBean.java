package com.jiangli.springboot.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Jiangli
 * @date 2017/2/27 15:23
 */
@Controller
public class ControllerBean {

    //    http://localhost:8080/modelAndView
    @RequestMapping("/modelAndView")
    ModelAndView mav() {
        System.out.println("mav");
        ModelAndView ret = new ModelAndView();
        ret.setViewName("ddd");
//        ret.setViewName("ddd");
//        ret.setViewName("ddd.jsp");
        return ret;
    }

    //    http://localhost:8080/modelAndView2
    @RequestMapping("/modelAndView2")
    String  mav2() {
        System.out.println("mav2");
//        return "ddd.tpl";
        return "ddd";
    }
}
