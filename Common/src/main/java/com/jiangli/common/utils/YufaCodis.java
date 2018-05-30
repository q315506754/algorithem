package com.jiangli.common.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2017/11/22 20:38
 */
public class YufaCodis {
    private String job = "开发-预发codis 操作（单条命令）";
    private String url = "http://192.168.9.252:8080/jenkins/job/";
    private String apiSuffix = "api/json";
    private String keyWord = "Finished: SUCCESS";
    private String userName = "kaifa";
    private String password= "KaiFa#ShangHai@2016";
    private boolean log = true;

    private void print(Object str) {
        if (log) {
            System.out.println(str);
        }
    }

    public YufaCodis() {
        job = job.replaceAll(" ", "%20");
    }

    public boolean isLog() {
        return log;
    }

    public YufaCodis setLog(boolean log) {
        this.log = log;
        return this;
    }

    public String getUrl(String command) {
        return url + job + "/" +command;
    }
    public String request(String cmd,UrlEncodedFormEntity entity) {
        try {
            DefaultHttpClient client = getClient();

            HttpPost post = new HttpPost(getUrl(cmd));
            post.setEntity(entity);

            // Execute your request with the given context
            HttpResponse response = client.execute(post, generateContext());
            HttpEntity responseEntity = response.getEntity();

            String result = EntityUtils.toString(responseEntity, "utf-8");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private String ts(long prev) {
        return "("+(System.currentTimeMillis() - prev) + " ms)";
    }

    public List<String> execute(String cmd) {
        List<String> ret = new LinkedList<>();
        try {
            long prev = System.currentTimeMillis();

            int oldVer = getLatestBuildVer();
            print("[!]旧版本:"+oldVer+ts(prev));

            //param
            List<NameValuePair> formParams = new ArrayList<>();
            formParams.add(new BasicNameValuePair("json","{\"parameter\": [{\"name\":\"command\", \"value\":\""+cmd+"\"}]}"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");

            //build
            prev = System.currentTimeMillis();
            request("build",entity);
            print("构建完毕..."+ts(prev));

            //sync version
            print("正在同步新版本...");
            prev = System.currentTimeMillis();
            int newVer= getLatestBuildVer();
            while (newVer==oldVer) {
                Thread.sleep(100);
                newVer= getLatestBuildVer();
            }
            print("[!]获得新版本号:"+newVer+ts(prev));

            //sync result
            print("正在查询控制台,build号:"+newVer);
            prev = System.currentTimeMillis();
            String request = request(newVer + "/consoleText", null);
            while (!request.contains(keyWord)) {
                Thread.sleep(100);
                request = request(newVer + "/consoleText", null);
            }
            print("[!]查到控制台结果..."+ts(prev));
            print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            print(request);
            print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            //parse to list
            String[] split = request.split("\n");

            boolean start= false;
            for (String line : split) {
                if ("输出结果:".equals(line)) {
                    start = true;
                    continue;
                }
                if (line.startsWith("SSH: EXEC: completed after")) {
                    break;
                }

                if (start) {
                    ret.add(line);
                }
            }

            print("-------------结束--------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private int getLatestBuildVer() {
        String jsonStr = request(apiSuffix,null);
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        JSONArray builds = jsonObject.getJSONArray("builds");
        JSONObject latestJSON = builds.getJSONObject(0);
        int number = latestJSON.getInt("number");
        return number;
    }

    private BasicHttpContext generateContext() {
        // Generate BASIC scheme object and stick it to the execution context
        BasicScheme basicAuth = new BasicScheme();
        BasicHttpContext context = new BasicHttpContext();
        context.setAttribute("preemptive-auth", basicAuth);
        return context;
    }

    private DefaultHttpClient getClient() {
        DefaultHttpClient client = new DefaultHttpClient();

        // Then provide the right credentials
        client.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(userName, password));

        // Add as the first (because of the zero) request interceptor
        // It will first intercept the request and preemptively initialize the authentication scheme if there is not
        client.addRequestInterceptor(new PreemptiveAuth(), 0);
        return client;
    }

    /**
     * Preemptive authentication interceptor
     *
     */
    public static class PreemptiveAuth implements HttpRequestInterceptor {

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
