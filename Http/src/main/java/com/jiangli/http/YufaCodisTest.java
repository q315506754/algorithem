package com.jiangli.http;

import java.util.List;

/**
 * @author Jiangli
 * @date 2017/11/22 20:48
 */
public class YufaCodisTest {
    public static void main(String[] args) {
        YufaCodis codis = new YufaCodis();
        codis.setLog(true);
        codis.setLog(false);
//        List<String> list = codis.execute("get aaa");
//        List<String> list = codis.execute("hget qa:operation:auditrecords:600102:2 contentShowStatus");

        List<String> qIdList = codis.execute("zrevrange qa:my:questionIds:167254279 0 -1");
        for (String qId : qIdList) {
            System.out.println("qId:"+qId);
            List<String> answerIdList = codis.execute("zrevrange qa:answer:qustionForAnswer:"+qId+" 0 -1");
            for (String answerId : answerIdList) {
                System.out.println("\t"+answerId);
            }
        }

//        System.out.println(list);


    }

}
