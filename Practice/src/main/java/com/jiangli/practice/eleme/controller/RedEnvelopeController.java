package com.jiangli.practice.eleme.controller;

import com.jiangli.practice.eleme.dao.RedEnvelopeRespository;
import com.jiangli.practice.eleme.model.RedEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2015/6/9 0009 10:39
 */
@Controller
@RequestMapping(value = "/rede")
public class RedEnvelopeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession session;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private RedEnvelopeRespository redEnvelopeRespository;


    /**
     * http://localhost:8080/dish/list
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    List<RedEnvelope> list() {

        List<RedEnvelope> list = redEnvelopeRespository.findList();

        logger.debug("RedEnvelope result:"+list);

        return list;
    }

}
