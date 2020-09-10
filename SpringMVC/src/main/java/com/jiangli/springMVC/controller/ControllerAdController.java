package com.jiangli.springMVC.controller;

import com.jiangli.common.model.Person;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangli
 * @date 2020/8/4 9:58
 */


//加了注解后  CommonController也能引用该Person属性
//    3个切面方法才起作用
@ControllerAdvice

//    Component无法替代ControllerAdvice
//@Component
public class ControllerAdController {

    /**
     * 定义了2个  但只有第1个被引用
     * 哪怕引用方写死了globalUser2也无法引用到
     */
    @ModelAttribute
    public Person globalUser() {
        Person user = new Person();
        user.setName("xxxY");
        user.setAge(23);
        user.setState("xxxYYYY");
        return user;
    }

    @ModelAttribute
    public Person globalUser2() {
        Person user = new Person();
        user.setName("yyyz");
        user.setAge(44);
        user.setState("yyyz");
        return user;
    }

    /**
     *
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView customException(HttpServletResponse res,Exception e) {
        res.setContentType("text/plain");
        try {
            e.printStackTrace();
            res.getWriter().println("wrong:"+e.getLocalizedMessage());
            res.getWriter().flush();//不加则输出会截断
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", e.getMessage());
        mv.setViewName("myerror");
        return mv;
    }

    @ModelAttribute(name = "md")
    public Map<String,Object> mydata() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("age", 99);
        map.put("gender", "男");
        return map;
    }
}
