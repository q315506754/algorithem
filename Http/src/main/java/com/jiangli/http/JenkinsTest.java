package com.jiangli.http;

import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiangli on 2017/8/13.
 */
public class JenkinsTest {
    public static void main(String[] args) throws Exception {
        String job = "开发-预发codis 操作（单条命令）";
        job = job.replaceAll(" ", "%20");
        DefaultHttpClient client = new DefaultHttpClient();

        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
//        formParams.add(new BasicNameValuePair("json",URLEncoder.encode("{\"parameter\": [{\"name\":\"command\", \"value\":\"high\"}]}")));
        formParams.add(new BasicNameValuePair("json","{\"parameter\": [{\"name\":\"command\", \"value\":\"zrange a 0 -1\"}]}"));
//        formParams.add(new BasicNameValuePair("json",URLEncoder.encode("{}")));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");

        // Then provide the right credentials
        client.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials("kaifa", "KaiFa#ShangHai@2016"));

        // Generate BASIC scheme object and stick it to the execution context
        BasicScheme basicAuth = new BasicScheme();
        BasicHttpContext context = new BasicHttpContext();
        context.setAttribute("preemptive-auth", basicAuth);

        // Add as the first (because of the zero) request interceptor
        // It will first intercept the request and preemptively initialize the authentication scheme if there is not
        client.addRequestInterceptor(new PreemptiveAuth(), 0);

        // You get request that will start the build
        String getUrl = "http://192.168.9.252:8080/jenkins/job/"+ job+"/build";
        System.out.println(getUrl);
//        String params = "json=" + URLEncoder.encode("{\"parameter\": [{\"name\":\"command\", \"value\":\"high\"}]}");
//        HttpGet get = new HttpGet(getUrl+"?"+params);
        HttpPost post = new HttpPost(getUrl);
        post.setEntity(entity);

        try {
            // Execute your request with the given context
            HttpResponse response = client.execute(post, context);
            HttpEntity responseEntity = response.getEntity();
//            EntityUtils.consume(entity);

            String result = EntityUtils.toString(responseEntity, "utf-8");
            System.out.println(result);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Preemptive authentication interceptor
     *
     */
    static class PreemptiveAuth implements HttpRequestInterceptor {

        /*
         * (non-Javadoc)
         *
         * @see org.apache.http.HttpRequestInterceptor#process(org.apache.http.HttpRequest,
         * org.apache.http.protocol.HttpContext)
         */
        public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
            // Get the AuthState
            AuthState authState = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);

            // If no auth scheme available yet, try to initialize it preemptively
            if (authState.getAuthScheme() == null) {
                AuthScheme authScheme = (AuthScheme) context.getAttribute("preemptive-auth");
                CredentialsProvider credsProvider = (CredentialsProvider) context
                        .getAttribute(ClientContext.CREDS_PROVIDER);
                HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
                if (authScheme != null) {
                    Credentials creds = credsProvider.getCredentials(new AuthScope(targetHost.getHostName(), targetHost
                            .getPort()));
                    if (creds == null) {
                        throw new HttpException("No credentials for preemptive authentication");
                    }
                    authState.setAuthScheme(authScheme);
                    authState.setCredentials(creds);
                }
            }

        }

    }
}
