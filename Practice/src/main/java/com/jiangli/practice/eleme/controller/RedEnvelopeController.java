package com.jiangli.practice.eleme.controller;

import com.jiangli.practice.eleme.dao.RedEnvelopeRepository;
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
    private RedEnvelopeRepository redEnvelopeRepository;


    /**
     * http://localhost:8080/dish/list
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    List<RedEnvelope> list() {

        List<RedEnvelope> list = redEnvelopeRepository.findList();

        logger.debug("RedEnvelope result:"+list);

        return list;
    }

    @RequestMapping(value = "/listAll", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    List<RedEnvelope> findListAll() {

        List<RedEnvelope> list = redEnvelopeRepository.findListAll();

        logger.debug("RedEnvelope listAll result:"+list);

        return list;
    }

    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    void remove(Integer id) {

       redEnvelopeRepository.delete(id);

    }

    @RequestMapping(value = "/enable", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    void enable(Integer id,Integer isEnable) {

        RedEnvelope one = redEnvelopeRepository.findOne(id);
        one.setIsEnable(isEnable);
        logger.debug("enable one:"+one);

       redEnvelopeRepository.save(one);

    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    void save(RedEnvelope redEnvelope) {

        logger.debug("redEnvelope one:"+redEnvelope);
        redEnvelopeRepository.save(redEnvelope);


    }

}
