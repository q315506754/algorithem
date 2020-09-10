package com.jiangli.springMVC.utils.iterutil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2020/6/6 10:57
 */
public class ShowDocDto {
    public String desc = "无";
    public String type = "POST";
    public String url = "外网请求链接";
    public String yu_url = "预发请求链接";
    public String local_url = "本地请求链接";
    public List<MethoParam> methods = new ArrayList<>();
}
