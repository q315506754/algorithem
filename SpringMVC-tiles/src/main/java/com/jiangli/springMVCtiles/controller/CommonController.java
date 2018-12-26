package com.jiangli.springMVCtiles.controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Jiangli
 *
 *         CreatedTime  2015/6/9 0009 10:39
 */
@Controller
@RequestMapping(value = "/")
public class CommonController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession session;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    public CommonController(ApplicationContext applicationContext) {
        logger.debug("CommonController construct...applicationContext:{}",applicationContext);
    }

    class A{
        void print() {
            System.out.println("absdf");
        }
    }

    //http://localhost:80/home1
    @RequestMapping("/home1")
    ModelAndView home(HttpServletRequest request) {
        //AnnotationConfigEmbeddedWebApplicationContext
//        or AnnotationConfigApplicationContext
        logger.debug("{}",Thread.currentThread());
        logger.debug("{}",request);
        return new ModelAndView("home/1");
    }

    //http://localhost:80/example
    @RequestMapping("/example")
    ModelAndView example(HttpServletRequest request) {
        //AnnotationConfigEmbeddedWebApplicationContext
//        or AnnotationConfigApplicationContext
        logger.debug("{}",request);
        return new ModelAndView("example/index");
    }

    //http://localhost:80/home2
    @RequestMapping("/home2")
    ModelAndView home2(HttpServletRequest request) {
        logger.debug("{}",Thread.currentThread());
        logger.debug("{}",request);
        //AnnotationConfigEmbeddedWebApplicationContext
//        or AnnotationConfigApplicationContext
        return new ModelAndView("home/2");
    }


    /**
     * http://localhost:80/eventinfo
     *
     * @return
     */
    @RequestMapping(value = "/eventinfo", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=utf-8")
    public
    @ResponseBody
    JSONObject eventinfo() {

        JSONObject param = new JSONObject();

        param.put("groups", "啊啊啊");

        param.put("subTypes", "吧cc");

        logger.debug("eventinfo result:"+param);

        return param;
    }

}
