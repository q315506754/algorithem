package com.jiangli.bytecode;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**

 https://arthas.aliyun.com/doc/quick-start.html

 PATH=/usr/lib64/qt-3.3/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/root/bin:/usr/local/jdk1.8.0_172/bin

PATH=$PATH:/usr/local/jdk1.8.0_172/bin
 echo $PATH

 curl -O https://arthas.aliyun.com/arthas-demo.jar
 java -jar arthas-demo.jar

 curl -O https://arthas.aliyun.com/arthas-boot.jar
 java -jar arthas-boot.jar

 redefine /root/OrderOpenServiceImpl.class

 base:
 1.dashboard
 5. quit/exit  stop
 6. reset
 7.history

 jvm
 1.1 sysprop
 1.2 vmoption
 3.thread 94
 4.jad com.zhihuishu.pay.core.openapi.wxpay.impl.WxSignOpenServiceImpl
 5.watch com.zhihuishu.pay.core.controller.baidu.callback.BaiduPayAsyncCallBackController asyncOrder returnObj
 6.perfcounter
 7. logger -n com.zhihuishu.pay.core.ZhishuihuPaycoreApplication
 7.1 logger --name com.zhihuishu.pay.core.ZhishuihuPaycoreApplication --level info
 7.2 logger -c 2aafb23c -n com.zhihuishu.pay.core.ZhishuihuPaycoreApplication --level info
 7.3 logger --include-no-appender
 8. getstatic com.zhihuishu.aries.api.operation.DailyQuotesShareController FILTERED_KEY
 https://commons.apache.org/proper/commons-ognl/language-guide.html
 9. ognl '@java.lang.System@out.println("hello")'
 9. ognl -c 6ea6d14e '@com.zhihuishu.aries.api.operation.DailyQuotesShareController@FILTERED_KEY'
 9. ognl -c 6ea6d14e '#value1=@System@getProperty("java.home"), #value2=@System@getProperty("java.runtime.name"), {#value1, #value2}'
 11. heapdump /tmp/dump.hprof
 11. heapdump --live /tmp/dump.hprof

 class/loader
 1. classloader -t
 1. classloader -t | grep LaunchedURLClassLoader | grep -v +-
 2. sc com.zhihuishu.aries.api.order.*
 2. sc -d com.zhihuishu.aries.api.order.*
 3. sm -d com.zhihuishu.aries.api.order.* query*
 3. sm  com.zhihuishu.aries.api.order.* query*
 4. jad  com.zhihuishu.aries.api.order.vo.PageRecordsCourseBookOrderVo
 4. jad  com.zhihuishu.aries.api.order.vo.PageRecordsCourseBookOrderVo equals
 4. jad  --source-only com.zhihuishu.aries.api.order.vo.PageRecordsCourseBookOrderVo equals
 5 mc
 6 redefine
 5.dump com.zhihuishu.aries.api.order.*


 monitor 诊断结束要执行 stop 或将增强过的类执行 reset 命令。
 1 monitor   com.zhihuishu.aries.api.user.UserController getUserInfoV2 -c 2
 计算条件表达式过滤统计结果(方法执行完毕之后)
 1 monitor   com.zhihuishu.aries.api.user.UserController getUserInfoV2 "params[0] <= 2" -c 2
 1 monitor   com.zhihuishu.aries.api.user.UserController getUserInfoV2 "params[0].length >= 2" -c 2
 在方法调用之前计算condition-express
 1 monitor   com.zhihuishu.aries.api.user.UserController getUserInfoV2 "params[0].length >= 2" -c 2 -b

 2 watch com.zhihuishu.aries.api.user.UserController getUserInfoV2 returnObj

 指定输出结果的属性遍历深度，默认为 1
 2 watch com.zhihuishu.aries.api.user.UserController getUserInfoV2 returnObj -x 4
 2 watch com.zhihuishu.aries.api.user.UserController getUserInfoV2 "{params,returnObj}" -x 4

 watch com.zhihuishu.aries.api2.edu.learncircle.InteractionControllerEdu saveArticleDynamic "{params,returnObj}" -x 4
 watch com.zhihuishu.aries.api.operation.DailyQuotesShareController quotesshare "{params,returnObj}" -x 4
 watch com.zhihuishu.aries.company.openapi.impl.CompanyOpenServiceImpl queryMySaasCompanyListWithRole "{params,returnObj}" -x 4
 watch com.zhihuishu.aries.service.impl.UserChooseCompanyImpl getUserCompany "{params,returnObj}" -x 3

 同时观察方法调用前和方法返回后
 2 watch com.zhihuishu.aries.api.user.UserController getUserInfoV2 "{params,returnObj}" -x 4 -b -s
 参数里-n 2，表示只执行两次

 2 watch com.zhihuishu.aries.api.user.UserController getUserInfoV2 "{params,returnObj}" "params[0].length >= 2" -x 4
 2 watch com.zhihuishu.aries.api.user.UserController getUserInfoV2 "{params,returnObj}" "#cost>200" -x 4

 3 stack  com.zhihuishu.aries.api.user.UserController getUserInfoV2

 4 trace  com.zhihuishu.aries.api.user.UserController getUserInfoV2

 4 trace  com.zhihuishu.aries.api.user.UserController injectInfo --listenerId 7
telnet localhost 3658

 watch com.zhihuishu.aries.user.service.impl.UserServiceImpl injectInfo "{params,returnObj}" -e -x 3

 Search-Class sc *ByteDto*
 sc -d *ByteDto*
 sc com.jiangli.*

 sc -d *TblGroupOpenServiceImpl*

 Search-Method sm com.jiangli.* *run*
 sm java.lang.String

 jad com.jiangli.bytecode.ByteDto

 redefine C:\\myprojects\\algorithem\\ByteCode\\target\\classes\\com\\jiangli\\bytecode\\ByteDto.class

 dump、jad 会导致redefine失效
 dump *ByteDto*

 classloader
 classloader -t
 classloader -l
 classloader -c 18b4aac2  -r META-INF/MANIFEST.MF | grep spring


 ----------- 监控 --------------

 monitor -c 2 com.jiangli.bytecode.ByteDto run

 watch com.jiangli.bytecode.ByteDto run #cost
 watch com.zhihuishu.aries.study.openapi.CompanyCoachOpenService listByDtoPagedJoinUserWithSort #cost



 watch com.jiangli.bytecode.ByteDto run "{params,returnObj}" -x 2
 watch com.jiangli.bytecode.ByteDto run "{target}" -x 2 -n 5
 watch com.jiangli.bytecode.ByteDto run "{target.integer}" -x 2 -n 5

 只能trace 1层 因为代价昂贵
 trace com.jiangli.bytecode.ByteDto run -n 5
 trace com.jiangli.bytecode.ByteDto run --skipJDKMethod false -n 5
 trace com.jiangli.bytecode.ByteDto run '#cost > 10' --skipJDKMethod false -n 5
 trace多层效果
 trace com.jiangli.bytecode.ByteDto run*
 trace com.jiangli.bytecode.ByteDto run* --skipJDKMethod false

 trace com.zhihuishu.aries.study.openapi.CompanyCoachOpenService listByDtoPagedJoinUserWithSort
 trace com.zhihuishu.aries.study.openapi.CompanyCoachOpenService listByDtoPagedJoinUserWithSort --skipJDKMethod false -n 5
 trace com.zhihuishu.aries.user.openapi.TblGroupOpenService getGroupTreeForOrganizationOfCompanyCoach --skipJDKMethod false -n 5

 #trace多个类或者多个函数
 可以用正则表匹配路径上的多个类和函数，一定程度上达到多层trace的效果。
 trace com.zhishi.org.api.saasweb.OrganizationController doQueryDepartTreeLazy
 trace -E com.zhishi.org.api.saasweb.OrganizationController doQueryDepartTreeLazy|queryTreeRootNode

#动态trace
 trace demo.MathGame run
 trace demo.MathGame primeFactors --listenerId 1

 stack  com.jiangli.bytecode.ByteDto run
 stack  com.jiangli.bytecode.ByteDto run3

 time tunnel

 tt -t com.jiangli.bytecode.ByteDto run*
 tt -l
 tt -i 1101
 tt -i 1101 -p --replay-times 2 --replay-interval 2000

 * @author Jiangli
 * @date 2019/11/26 9:38
 *
 */
public class ByteDto implements Runnable, Serializable {
    @Autowired
    private Integer integer;
    protected String s="aa";
    private static double aDouble;
    public static String str = "ABCD";

    @Override
    public void run() {
        System.out.println("hello22 " + str);
        run2();
    }

    public void run2() {
        System.out.println("run2.... " + str);
        run3();
    }

    public void run3() {
        System.out.println("run3.... " + str);
    }
}
