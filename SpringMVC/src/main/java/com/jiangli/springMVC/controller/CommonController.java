package com.jiangli.springMVC.controller;

import com.jiangli.springMVC.B;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * @author Jiangli
 *
 *         CreatedTime  2015/6/9 0009 10:39
 */
@Controller
@RequestMapping(value = "/")
@SessionAttributes({"sesA","x"})
public class CommonController extends BaseController{

    @Autowired
    private HttpSession session;

    @Autowired
    private ServletContext servletContext;


    public CommonController() {
        logger.debug("CommonController construct...");
    }


    class A{
        String name;

        void print() {
            System.out.println("absdf");
        }
    }


    @ModelAttribute
    A propA() {
        A ret = new A();
        ret.name = "阿打算as";
        logger.debug("propA:{}",ret);
        return ret;
    }

    @ModelAttribute("sesA")
    A sesA() {
        A ret = new A();
        ret.name = "session值";
        logger.debug("sesA:{}",ret);
        return ret;
    }

    @ModelAttribute("propBB")
    int propB() {
        int ret = new Random().nextInt();
        logger.debug("propB:{}",ret);
        return ret;
    }
    @ModelAttribute
    int propC() {
        int ret = 444;
        logger.debug("propC:{}",ret);
        return ret;
    }

    //http://localhost:80/mv/ABC?a=123
    @RequestMapping("/mv/{id}")
    ModelAndView mv(HttpServletRequest request
        ,@PathVariable("id") String id

        ,@CookieValue("JSESSIONID") String JSESSIONID
        ,@RequestHeader("User-Agent") String UserAgent
        ,@RequestParam("a") String a

        ,@ModelAttribute() A obj
        ,@ModelAttribute("int") Integer c
        ,@ModelAttribute("propBB") Integer b

        ,Model model
        ,HttpSession session

    ) {
        logger.debug("mv,{},{},{},{},{}",JSESSIONID,UserAgent,a,id);
        logger.debug("obj:{}",obj);
        logger.debug("b:{}",b);
        logger.debug("c:{}",c);
        Object x = session.getAttribute("x");
        logger.debug("x:{}", x);

        logger.debug("model:{}",model);

        ModelAndView modelAndView = new ModelAndView("mv");
        modelAndView.addObject("put", "AAA");
        modelAndView.addObject("obj", obj);
        modelAndView.addObject("b", b);

        if (x == null) {
            modelAndView.addObject("x", 0);
        } else {
            modelAndView.addObject("x", (int)x+1);
        }

        return modelAndView;
    }

    //http://localhost:8080/home1
    @RequestMapping("/home1")
    String home(HttpServletRequest request) {
        //AnnotationConfigEmbeddedWebApplicationContext
//        or AnnotationConfigApplicationContext
        System.out.println(Thread.currentThread());
        System.out.println(request);
        return "Hello World!Example~";
    }

    //http://localhost:8080/home2
    @RequestMapping("/home2")
    String home2(HttpServletRequest request) {
        System.out.println(Thread.currentThread());
        System.out.println(request);
        //AnnotationConfigEmbeddedWebApplicationContext
//        or AnnotationConfigApplicationContext
        return "Hello World!Example~";
    }

    /**
     * http://localhost:8081/rmi.go
     *
     * @param id
     *
     * @return
     */
    @RequestMapping(value = "/rmi", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=utf-8")
    public
    @ResponseBody
    String rmi(String id, HttpServletRequest request) {
        Object guguday = WebUtils.getSessionAttribute(request, "guguday");
        logger.debug("guguda222988882228啊 is:{}",guguday);

        int[] values = {1, 2, 3};
        boolean b = false;

        //values.
        //guguday.
//        new org.apache.commons.mail.HtmlEmail();
        new A().print();
        new B().func();
        try {
            logger.debug("servletContext {}", servletContext);
            logger.debug("web getSessionId {}", WebUtils.getSessionId(request));
            logger.debug("web getSessionAttribute-aaa {}",request.getAttribute("aaa"));
            logger.debug("web getSessionAttribute-bbb {}", request.getAttribute("bbb"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "execute..." + String.valueOf("ddd");
    }

    /**
     * http://localhost:8081/autoc/get
     *
     * @return
     */
    @RequestMapping(value = "/autoc/get", method = {RequestMethod.POST, RequestMethod.GET})
//    @RequestMapping(value = "/autoc/get", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=utf-8")
    public
    @ResponseBody
    JSONArray autoc(String type, String key, Integer size) {
        JSONArray arr = new JSONArray();

        JSONObject param = new JSONObject();
        param.put("key", key);
        param.put("type", type);
        if (size == null) {
            size = 10;
        }
        param.put("size", size);

        try {
            logger.debug("request param:" + param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arr;
    }

    @RequestMapping(value = "/eventinfo", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=utf-8")
//    @RequestMapping(value = "/eventinfo", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    JSONObject eventinfo() {

        JSONObject param = new JSONObject();

        param.put("groups", "fsdfsdf");

        param.put("subTypes", "bbbb");

        logger.debug("eventinfo result:"+param);

        return param;
    }
//
//    @RequestMapping(value = "/hotelPopInfo", method = {RequestMethod.POST, RequestMethod.GET})
//    public ModelAndView popInfo(HttpServletRequest request, String pName) {
//        logger.debug(pName);
//
//        JSONObject data = ccorder.getHotelPropInfo(pName);
//        request.setAttribute("hotelInfo", data);
//        logger.debug("-----"+data);
//        //TODO
//        ModelAndView ret = new ModelAndView("module/hotelPopInfo");
//        return ret;
//    }

}
