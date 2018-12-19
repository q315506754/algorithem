package com.jiangli.db.dao.ext;

/**
 * @author Jiangli
 * @date 2018/12/19 15:47
 */
public class MyProvider {
    public String dynamicSQL(Object p) {
        System.out.println("p:"+p);
        return "<script>select * from TBL_USER</script>";
    }
}
