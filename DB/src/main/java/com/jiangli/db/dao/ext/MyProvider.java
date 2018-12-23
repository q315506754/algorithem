package com.jiangli.db.dao.ext;

/**
 * @author Jiangli
 * @date 2018/12/19 15:47
 */
public class MyProvider {
    public String dynamicSQL(Object p) {
        System.out.println("p:"+p);
        //return "select * from TBL_USER where IS_DELETED=0";
        return "select * from TBL_USER where IS_DELETED=#{isDeleted}";
    }
}
