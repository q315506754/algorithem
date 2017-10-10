package com.jiangli.crawler.webmagic;

import com.jiangli.common.utils.PathUtil;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jiangli
 * @date 2017/10/10 15:33
 */
public class HttpsDisableUtil {
    public static void disableSSLCertCheck() throws NoSuchAlgorithmException, KeyManagementException {
        //vm
        //-Djavax.net.debug=ssl
        //-Djavax.net.debug=ssl,keymanager

        //firefox export baiduyun.crt
        //keytool -import -v -file D:\pan.baidu.com.crt -keystore D:\pan.baidu.com.jks -storepass baiducom

        String path = PathUtil.buildPath(PathUtil.getProjectPath("WebCrawler").getProjectBasePath(), PathUtil.PATH_SRC_MAIN_RESOURCES, "pan.baidu.com.jks");
//        System.out.println(path);
        System.setProperty("javax.net.ssl.trustStore", path);
//        System.setProperty("javax.net.ssl.keyPassword", "baiduyun");
        System.setProperty("javax.net.ssl.keyStorePassword", "baiducom");


        SslUtils.ignoreSsl();
//        // Create a trust manager that does not validate certificate chains
//        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
//            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                return null;
//            }
//            public void checkClientTrusted(X509Certificate[] certs, String authType) {
//            }
//            public void checkServerTrusted(X509Certificate[] certs, String authType) {
//            }
//        }
//        };
//
//        // Install the all-trusting trust manager
//        SSLContext sc = SSLContext.getInstance("SSL");
//        sc.init(null, trustAllCerts, new java.security.SecureRandom());
//        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//
//        // Create all-trusting host name verifier
//        HostnameVerifier allHostsValid = new HostnameVerifier() {
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        };
//
//        // Install the all-trusting host verifier
//        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
}
