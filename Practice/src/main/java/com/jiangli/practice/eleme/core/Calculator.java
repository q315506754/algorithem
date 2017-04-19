package com.jiangli.practice.eleme.core;

import com.jiangli.practice.eleme.dao.DishRespository;
import com.jiangli.practice.eleme.dao.MerchantRespository;
import com.jiangli.practice.eleme.dao.RuleRespository;
import com.jiangli.practice.eleme.model.Dish;
import com.jiangli.practice.eleme.model.Merchant;
import com.jiangli.practice.eleme.model.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/18 13:35
 */
@Component
public class Calculator {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DishRespository dishRespository;

    @Autowired
    private MerchantRespository merchantRespository;

    @Autowired
    private RuleRespository ruleRespository;

    public void calc(CalcContext context) {
        Integer merchantId = context.getMerchantId();
        Merchant merchant=merchantRespository.findOne(merchantId);
        List<Dish> selectedDishes=dishRespository.findByMerchantId(merchantId);
        List<Rule> rules=ruleRespository.findByMerchantIdOrderBySortAsc(merchantId);
        logger.debug("merchant:"+merchant);
        logger.debug("selectedDishes:"+selectedDishes);
        logger.debug("rules:"+rules);


    }
}
