package com.jiangli.practice.eleme.controller;

import com.jiangli.practice.eleme.dao.MerchantRepository;
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
    private MerchantRepository merchantRepository;


    /**
     * http://localhost:8080/merchant/list
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    List<Merchant> list() {

        List<Merchant> list = merchantRepository.findAllOrderByLikeitDesc();

        logger.debug("merchants result:"+list);

        return list;
    }

    @RequestMapping(value = "/like", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
   void like(Integer id,Integer like) {
        merchantRepository.setLikeit(id,like);
        logger.debug("like id:{} like:{}",id,like);
    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
   void save(Merchant merchant) {
        logger.debug("save merchant:{}",merchant);
        merchantRepository.save(merchant);
    }
}
