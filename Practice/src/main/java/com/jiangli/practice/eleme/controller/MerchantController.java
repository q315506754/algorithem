package com.jiangli.practice.eleme.controller;

import com.jiangli.practice.eleme.dao.MerchantRespository;
import com.jiangli.practice.eleme.model.Merchant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2015/6/9 0009 10:39
 */
@Controller
@RequestMapping(value = "/merchant")
public class MerchantController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession session;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private MerchantRespository merchantRespository;


    /**
     * http://localhost:8080/merchant/list
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    List<Merchant> list() {

        Iterable<Merchant> all = merchantRespository.findAll();
        List<Merchant> merchants = new ArrayList<>();
        for (Merchant merchant : all) {
            merchants.add(merchant);
        }

        logger.debug("merchants result:"+merchants);

        return merchants;
    }

}
