package com.jiangli.practice.eleme.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

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


    public CommonController() {
        logger.debug("CommonController construct...");
    }


    @RequestMapping(value = "/", method = { RequestMethod.GET})
    public String root() {
        return "redirect:index";
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
        logger.debug("guguday is:{}",guguday);

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


    @RequestMapping(value = "/index", method = { RequestMethod.GET})
    public
    String index() {
        return "index";
    }

}
