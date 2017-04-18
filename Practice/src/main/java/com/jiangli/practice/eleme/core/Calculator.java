package com.jiangli.practice.eleme.core;

import com.jiangli.practice.eleme.model.Dish;
import com.jiangli.practice.eleme.model.Merchant;
import com.jiangli.practice.eleme.model.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/18 13:35
 */
@Component
public class Calculator {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public void calc(CalcContext context) {
        Merchant merchant=null;
        List<Dish> selectedDishes=null;
        List<Rule> rules=null;
        logger.debug("calc");
    }
}
