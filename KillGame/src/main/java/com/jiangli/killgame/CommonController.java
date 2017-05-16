package com.jiangli.killgame;

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


        return "execute..." + String.valueOf("ddd");
    }

}
