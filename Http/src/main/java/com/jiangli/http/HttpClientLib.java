package com.jiangli.http;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiangli on 2017/8/13.
 */
public class HttpClientLib {
    public static void main(String[] args) throws Exception {
        String url = "https://api.github.com/repos/OpenFeign/feign/contributors";
//        String url = "https://www.baidu.com/";
//        String url = "http://api.github.com/repos/OpenFeign/feign/contributors";


        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
//        formParams.add(new BasicNameValuePair("a","v"));

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
//        HttpPost post = new HttpPost(url);
        HttpGet post = new HttpGet(url);
//        post.setEntity(entity);


        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(post);
        System.out.println(response.getStatusLine().getStatusCode());

        response.setHeader("content-type", "text/html;charset=UTF-8");


        HttpEntity responseEntity = response.getEntity();
        System.out.println(responseEntity);
        // response.getStatusLine().getStatusCode();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // return "1";
        } else {
//				System.out.println("getPostResponse error,statusCode=" + response.getStatusLine().getStatusCode() + "url=" + url + ";response=" + EntityUtils.toString(responseEntity));
        }
        String result = EntityUtils.toString(responseEntity, "utf-8");
        System.out.println(result);
    }
}
