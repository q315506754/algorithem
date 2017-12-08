package com.jiangli.http;

/**
 * @author Jiangli
 * @date 2017/11/22 20:48
 */
public class YufaCodisTest {
    public static void main(String[] args) {
        YufaCodis codis = new YufaCodis();
        codis.setLog(true);
//        codis.setLog(false);
//        List<String> list = codis.execute("get aaa");
//        List<String> list = codis.execute("hget qa:operation:auditrecords:600102:2 contentShowStatus");

//        List<String> qIdList = codis.execute("zrevrange qa:my:questionIds:167254279 0 -1");
//        for (String qId : qIdList) {
//            System.out.println("qId:"+qId);
//            List<String> answerIdList = codis.execute("zrevrange qa:answer:qustionForAnswer:"+qId+" 0 -1");
//            for (String answerId : answerIdList) {
//                System.out.println("\t"+answerId);
//            }
//        }

//        System.out.println(list);

//        System.out.println( codis.execute("hgetall qa:question:hash:1083"));
//        System.out.println( codis.execute("hgetall qa:question:hash:3123"));
        //12名师api缓存
        System.out.println( codis.execute("del " +
                "th:openapi:teachers:remoteresult:src:1 " +
                "th:openapi:teachers:remoteresult:src:1:lock " +
                //黄天中
//                "th:webcontroller:teacher:courseIdsStudyCount:171 " +
//                "th:webcontroller:teacher:courseIdsStudyCount:171:lock " +
//                "th:webcontroller:teacher:courseIdsStudyTotalCount:171 " +
//                "th:webcontroller:teacher:courseIdsStudyTotalCount:171:lock " +
                //于海
                "th:webcontroller:teacher:courseIdsStudyCount:29 " +
                "th:webcontroller:teacher:courseIdsStudyCount:29:lock " +
                "th:webcontroller:teacher:courseIdsStudyTotalCount:29 " +
                "th:webcontroller:teacher:courseIdsStudyTotalCount:29:lock "
                )
        );


    }

}
