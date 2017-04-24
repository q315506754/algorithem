package com.jiangli.practice.eleme.controller;

import com.jiangli.practice.eleme.dao.DishRepository;
import com.jiangli.practice.eleme.model.Dish;
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
@RequestMapping(value = "/dish")
public class DishController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession session;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private DishRepository dishRepository;


    /**
     * http://localhost:8080/dish/list
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    List<Dish> list(Integer merchantId) {

        List<Dish> list = dishRepository.findByMerchantId(merchantId);

        logger.debug("Dish result:"+list);

        return list;
    }

    @RequestMapping(value = "/like", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    void like(Integer id,Integer like) {
        dishRepository.setLikeit(id,like);
        logger.debug("like id:{} like:{}",id,like);
    }

    @RequestMapping(value = "/inc", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    void inc(Integer id,Integer inc) {
        dishRepository.incTimes(id,inc);
        logger.debug("inc id:{} inc:{}",id,inc);
    }


    @RequestMapping(value = "/save", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    void save(Dish dish) {
        logger.debug("save dish:{}",dish);
        dishRepository.save(dish);
    }
}
