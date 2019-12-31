package com.jiangli.common.utils;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @author JiangLi
 *
 *         CreateTime 2013-12-3 下午4:47:52
 */
public class HttpPostUtil {
    //	private final static Logger log = Logger.getLogger(HttpPostUtil.class);
    private static final Log log = LogFactory.getLog(HttpPostUtil.class);

    /**
     * funtions
     *
     * @param url
     * @param params
     *
     * @return String
     *
     * @author JiangLi CreateTime 2013-12-2 下午1:36:19
     */
    public static String postUrl(String url, Map params) {
//
        return postUrl(url, params,new HashMap<>());
    }

    public interface ReqInterceptor<T>{
        void interceptBeforeReq(Map params,Map<String,String> headers,T token);

        boolean judgeResultError(String data);

        T getCachedToken(Map params,Map<String,String> headers);

        T refreshToken(Map params,Map<String,String> headers);
    }

    public static class  ReqInterceptorAdaptor<T> implements ReqInterceptor<T> {
        private boolean log=true;
        private boolean isEmptyInter=false;
        private ReqInterceptor<T> reqInterceptor;

        public boolean isLog() {
            return log && !isEmptyInter;
        }

        public void setLog(boolean log) {
            this.log = log;
        }

        public ReqInterceptorAdaptor(boolean log, ReqInterceptor<T> reqInterceptor) {
            setLog(log);
            if (reqInterceptor != null) {
                this.reqInterceptor = reqInterceptor;
            } else {
                this.isEmptyInter = true;
                this.reqInterceptor = new ReqInterceptor<T>() {
                    @Override
                    public void interceptBeforeReq(Map params, Map<String, String> headers, T token) {

                    }

                    @Override
                    public boolean judgeResultError(String data) {
                        return false;
                    }

                    @Override
                    public T getCachedToken(Map params, Map<String, String> headers) {
                        return null;
                    }

                    @Override
                    public T refreshToken(Map params, Map<String, String> headers) {
                        return null;
                    }
                };
            }
        }

        public ReqInterceptorAdaptor(ReqInterceptor<T> reqInterceptor) {
            this(false,reqInterceptor);
        }

        public void interceptBeforeReq(Map params, Map<String, String> headers, T token) {
            if (isLog()) {
                //System.out.println("params 处理前:"+params);
                //System.out.println("headers 处理前:"+headers);
            }
            reqInterceptor.interceptBeforeReq(params, headers, token);
            if (isLog()) {
                System.out.println("params 处理后:"+params);
                System.out.println("headers 处理后:"+headers);
            }
        }

        @Override
        public boolean judgeResultError(String data) {
            boolean b = reqInterceptor.judgeResultError(data);

            if (b) {
                if (isLog()) {
                    System.err.println("请求出现异常:"+data);
                }
            }

            return b;
        }

        public T getCachedToken(Map params, Map<String, String> headers) {
            T cachedToken = reqInterceptor.getCachedToken(params, headers);
            if (isLog()) {
                System.out.println("cached token:"+cachedToken);
            }
            return cachedToken;
        }

        public T refreshToken(Map params, Map<String, String> headers) {
            T refreshToken = reqInterceptor.refreshToken(params, headers);
            if (isLog()) {
                System.out.println("refreshToken:"+refreshToken);
            }
            return refreshToken;
        }
    }

    public static <T> String postUrl(String url, Map params,Map<String,String> headers,ReqInterceptor<T> interceptor) {
        log.debug("HttpPostUtil.postUrl:" + url + " params:" + params);
        String result = "";


        if (headers == null) {
            headers = new HashMap<>();
        }

        ReqInterceptorAdaptor<T> adaptor = new ReqInterceptorAdaptor(false,interceptor);

        T token = adaptor.getCachedToken(params, headers);
        adaptor.interceptBeforeReq(params, headers,token);

        org.apache.http.client.HttpClient client = new DefaultHttpClient();
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        if (params != null) {
            Set keySet = params.keySet();
            for (Object k : keySet) {
                formParams.add(new BasicNameValuePair(k.toString(), params.get(k.toString()).toString()));
            }
        }
        HttpPost post = null;
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            post = new HttpPost(url);
            post.setEntity(entity);
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    post.addHeader(entry.getKey(),entry.getValue());
                }
            }

            HttpResponse response = client.execute(post);
            response.setHeader("content-type", "text/html;charset=UTF-8");
            HttpEntity responseEntity = response.getEntity();
            // response.getStatusLine().getStatusCode();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // return "1";
            } else {
                //				System.out.println("getPostResponse error,statusCode=" + response.getStatusLine().getStatusCode() + "url=" + url + ";response=" + EntityUtils.toString(responseEntity));
            }

            result = EntityUtils.toString(responseEntity, "utf-8");
            //            log.debug("[RS]HttpPostUtil.postUrl url:" + url + " result:" + result);
            //            log.debug("[RS]HttpPostUtil.postUrl params:" + params );
            //            log.debug("[RS]HttpPostUtil.postUrl formParams:" + formParams );
            //            log.debug("[RS]HttpPostUtil.postUrl result:" + result );

            boolean hasTokenError = adaptor.judgeResultError(result);

            if (hasTokenError) {
                T refreshToken = adaptor.refreshToken(params, headers);

                adaptor.interceptBeforeReq(params, headers,refreshToken);

            //    retry one-time
                return postUrl(url, params, headers,null);
            }
        } catch (Exception e) {
            //			e.printStackTrace();
            //			System.err.println(e.getMessage() + " " +url + " " + params);
            log.error(e.getMessage(), e);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
        return result;
    }

    public static String postUrl(String url, Map params,Map<String,String> headers) {
//
        return postUrl(url, params, headers,null);
    }

    public static PostResult postUrlGetObj(String url, JSONObject params) {
        PostResult ret = new PostResult();

        org.apache.http.client.HttpClient client = new DefaultHttpClient();

        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        if (params != null) {
            Set keySet = params.keySet();
            for (Object k : keySet) {
                formParams.add(new BasicNameValuePair(k.toString(), params.getString(k.toString())));
            }
        }

        HttpPost post = null;
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            post = new HttpPost(url);
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            response.setHeader("content-type", "text/html;charset=UTF-8");

            ret.statusLine = response.getStatusLine().toString();
            ret.statusCode = response.getStatusLine().getStatusCode();

            HttpEntity responseEntity = response.getEntity();
            ret.result = EntityUtils.toString(responseEntity, "utf-8");
        } catch (Exception e) {
            ret.e = e;
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
        return ret;
    }

    public static String getUrlRequest(String url, JSONObject params) {
        String realUrl = getUrl(url, params);
        try {
            //URL url1 = new URL(realUrl);
            //URLConnection urlConnection = url1.openConnection();
            //urlConnection.connect();

            return IOUtils.toString(new URL(realUrl), Charset.defaultCharset() );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUrl(String url, JSONObject params) {
//		log.debug("HttpPostUtil.getUrl:" + url + " params:" + params);
        if (params != null) {
            String paramStr = "?";

            Set keySet = params.keySet();
            Iterator iterator = keySet.iterator();
            while (iterator.hasNext()) {
                Object k = iterator.next();
                String temp = k.toString() + "=";
                Object object = params.get(k);
                temp += String.valueOf(object);
                if (iterator.hasNext()) {
                    temp += "&";
                }
                paramStr += temp;
            }

            url += paramStr;
        }

        return url;
    }

    public static String writeData(String url, JSONObject params) {
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());

            // Configure and open a connection to the site you will send the
            // request
            URL uRL = new URL(url);
            HttpsURLConnection urlConnection = (HttpsURLConnection) uRL.openConnection();

            urlConnection.setSSLSocketFactory(sc.getSocketFactory());
            urlConnection.setHostnameVerifier(new TrustAnyHostnameVerifier());

            // 设置doOutput属性为true表示将使用此urlConnection写入数据
            urlConnection.setDoOutput(true);
            // urlConnection.setReadTimeout(10000);
            // 定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型
            urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 得到请求的输出流对象
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            if (params != null) {
                // 把数据写入请求的Body
                out.write(params.toString());
            }
            out.flush();
            out.close();

            // 从服务器读取响应
            InputStream inputStream = urlConnection.getInputStream();
            String encoding = urlConnection.getContentEncoding();
            String body = IOUtils.toString(inputStream, encoding);
            // System.out.println(body);
            return body;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class PostResult {
        public String statusLine;
        public int statusCode;
        public String result;
        public Exception e;

        @Override
        public String toString() {
            return "PostResult{" +
                    "statusLine='" + statusLine + '\'' +
                    ", statusCode=" + statusCode +
                    ", result='" + result + '\'' +
                    ", e=" + e +
                    '}';
        }
    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
