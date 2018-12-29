package com.jiangli.springMVCflow.controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jiangli
 *
 *         CreatedTime  2015/6/9 0009 10:39
 */
@Controller
@RequestMapping(value = "/ajax")
public class AjaxFragmentController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/frags", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=utf-8")
    public
    @ResponseBody
    JSONObject frags() {
        JSONObject param = new JSONObject();
        param.put("groups", "frags");
        param.put("subTypes", "frags");
        logger.debug("frags result:"+param);
        return param;
    }

}
