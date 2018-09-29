package com.jiangli.doc.sql.helper.zhihuishu.bbs;

import org.apache.commons.lang.StringUtils;

import java.net.URLDecoder;
import java.util.Set;
import java.util.SortedMap;

/**
 * @Description:
 * @Auther: dai wei
 * @Date: 2018/8/7 17:24
 * @param:
 */
public class SortUtils {

    /**
     *
     * 功能描述: 获取 treemap 所有values
     * @auther: daiwei
     * @date: 2018/8/7 17:36
     * @param:
     * @return:
     */
    public static String getTreeMapValues(SortedMap<String,String> map){
        String str = "";
        if (map!=null && map.size() > 0 ){
            Set<String> keys = map.keySet();
            for (String key : keys) {
                if (StringUtils.isNotEmpty(map.get(key))){
                    str += URLDecoder.decode(map.get(key));
                }
            }
        }
        return str;
    }

}
