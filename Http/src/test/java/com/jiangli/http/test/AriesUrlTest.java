package com.jiangli.http.test;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Jiangli
 * @date 2019/5/22 9:17
 */
public class AriesUrlTest {
    public static void main(String[] args) throws Exception {
        URL urls1 = new URL("http://image.g2s.cn/zhs_yanfa_150820/apparies/userAvatar/201905/4f2b114058754280a1e8ad5a29a8f94e.docx?x-oss-process=image/resize,m_lfit,w_100,h_100");
        URLConnection conns1 = urls1.openConnection();
        InputStream inputs1 = conns1.getInputStream();
        System.out.println(inputs1);
    }

}
