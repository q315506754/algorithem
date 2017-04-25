package com.jiangli.practice.eleme.controller;

import com.jiangli.practice.eleme.core.*;
import com.jiangli.practice.eleme.dao.MerchantRepository;
import com.jiangli.practice.eleme.dao.RuleRespository;
import com.jiangli.practice.eleme.param.PreviewModel;
import com.jiangli.practice.eleme.param.TestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2015/6/9 0009 10:39
 */
@Controller
@RequestMapping(value = "/calc")
public class CalcController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession session;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private Calculator calculator;

    @Autowired
    private RuleRespository ruleRespository;

    @RequestMapping(value = "/listtest", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    void list(
            @RequestParam(value = "items[]", required = false) List<Item> items,
            @RequestParam(value = "items2[][]", required = false) List<Item> items2,
//            List<Item> items3,
            @RequestParam(value = "itemsArr[]", required = false) Item[] itemsArr,
              int[] iArr,
              @RequestParam(value = "iListArr[]", required = false) List<Integer> iListArr,
              @RequestParam(value = "iListArr2", required = false) List<Integer> iListArr2
    ) {
        logger.debug("items:"+items);
        logger.debug("items2:"+items2);
//        logger.debug("items3:"+items3); //Failed to instantiate [java.util.List]: Specified class is an interface

        logger.debug("itemsArr:"+String.valueOf(itemsArr));
        if (itemsArr!=null) {
            logger.debug("itemsArr:"+ Arrays.toString(itemsArr));
        }

        logger.debug("iArr:"+String.valueOf(iArr));
        if (itemsArr!=null) {
            logger.debug("iArr:"+ Arrays.toString(iArr));
        }

        logger.debug("iListArr:"+iListArr);
        logger.debug("iListArr2:"+iListArr2);

    }

    @RequestMapping(value = "/listtest2", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    void listtest2(
            TestModel testModel
    ) {
        logger.debug("items5:"+testModel);
        logger.debug("items5 getItems:"+testModel.getItems5());
    }

    @RequestMapping(value = "/preview", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    Solution preview(
            PreviewModel previewModel
    ) {
        logger.debug("previewModel:"+previewModel);
        logger.debug("previewModel getItems:"+previewModel.getItems());
        logger.debug("previewModel getMerchantId:"+previewModel.getMerchantId());

        CalcContext context = new CalcContext();

        Cart cart = new Cart();
        context.setCart(cart);

        cart.setItems(previewModel.getItems());

        context.setMinOrder(1);
        context.setMaxOrder(1);

        context.setMerchantId(previewModel.getMerchantId());

        calculator.calc(context);

        List<Solution> solutions = context.getSolutions();
        return solutions.get(0);
    }
}
