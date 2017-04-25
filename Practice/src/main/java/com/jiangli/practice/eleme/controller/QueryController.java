package com.jiangli.practice.eleme.controller;

import com.jiangli.common.core.ThreadCollector;
import com.jiangli.practice.eleme.core.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * @author Jiangli
 *
 *         CreatedTime  2015/6/9 0009 10:39
 */
@Controller
@RequestMapping(value = "/query")
public class QueryController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession session;

    @Autowired
    private ServletContext servletContext;


    @Autowired
    private Calculator calculator;

    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ModelAndView index(
            String queryId
    ) {
        logger.debug("queryId:" + queryId);
        ModelAndView modelAndView = new ModelAndView("query");
        modelAndView.addObject("queryId",queryId);
        return modelAndView;
    }

    @RequestMapping(value = "/process", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ThreadCollector.QueryResult process(
            String queryId
    ) {
        logger.debug("queryId:" + queryId);
        ThreadCollector.QueryResult rs = ThreadCollector.query(queryId);
        logger.debug("rs:" + rs);
        return rs;
    }
}
