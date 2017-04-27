package com.jiangli.practice.eleme.controller;

import com.jiangli.practice.eleme.dao.RuleRepository;
import com.jiangli.practice.eleme.model.Rule;
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
@RequestMapping(value = "/rule")
public class RuleController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession session;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private RuleRepository ruleRepository;



    @RequestMapping(value = "/findList", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    List<Rule> findList(Integer merchantId) {

        List<Rule> listForMerchant = ruleRepository.findListForMerchant(merchantId);

        return listForMerchant;

    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    void save(Rule rule) {

        logger.debug("Rule one:"+rule);
        ruleRepository.save(rule);

    }


    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    void remove(Integer id) {
        ruleRepository.delete(id);
    }

}
