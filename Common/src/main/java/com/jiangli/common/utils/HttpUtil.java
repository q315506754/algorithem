package com.jiangli.common.utils;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @Description:发送http请求帮助类
 * @author:liuyc
 * @time:2016年5月17日 下午3:25:32
 */
public class HttpUtil {

    public static String sendGet(String urlParam, Map<String, Object> params) {
        return sendGet(urlParam, params,"UTF-8");
    }

    public static String sendGet(String urlParam, Map<String, Object> params, String charset) {
        StringBuffer resultBuffer = null;
        // 构建请求参数
        HttpURLConnection con = null;
        BufferedReader br = null;
        try {
            URL url = new URL(urlParam +concatParams(params));
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.connect();
            resultBuffer = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String temp;
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                } finally {
                    if (con != null) {
                        con.disconnect();
                        con = null;
                    }
                }
            }
        }
        return resultBuffer.toString();
    }
    public static String concatParams(Map<String, Object> params,boolean withQuestion) {
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        if (sbParams != null && sbParams.length() > 0) {
            return (withQuestion?"?":"") +sbParams.substring(0, sbParams.length() - 1);
        }else {
            return "";
        }
    }

    public static String concatParams(Map<String, Object> params) {
        return concatParams(params, true);
    }

    public static String sendPostP(String url, Map<String, Object> params) {
        return sendPost(url,concatParams(params,false));
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //1.获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //2.中文有乱码的需要将PrintWriter改为如下
            //out=new OutputStreamWriter(conn.getOutputStream(),"UTF-8")
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args)  throws IllegalAccessException, InstantiationException {
        Map<String, Object> params=new HashMap<>();
        params.put("user_id","37");
        params.put("ts","1528854470");
        params.put("token","5aeb66e2");
        params.put("partner","zhs");
        String data= sendGet("http://moocnd.t.esile.me/student/partner/user",params,"UTF-8");
        String data2= sendPost("http://api.g2s.cn/interaction/dailyShowStaticData",null);
        System.out.println("sendGet map:"+data);
        System.out.println("sendPost str:"+data2);

        Map<String, Object> params2=new HashMap<>();
        params2.put("courseId","1");
        params2.put("type","123");
        params2.put("targetId","1");
        System.out.println("sendPost map:"+sendPostP("http://api.g2s.cn/baseCourse/getAudioContentByChapterId",params2));
        System.out.println("sendPost str:"+sendPost("http://api.g2s.cn/baseCourse/getAudioContentByChapterId","courseId=1&type=123&targetId=1"));

        //  String data2= ArmyHttpClientUtils.jsonPost("http://api.g2s.cn/interaction/dailyShowStaticData?",params);
        JSONObject obj= JSONObject.fromObject(data);
        JSONObject obj2= JSONObject.fromObject(data2);
        JSONObject obj3= JSONObject.fromObject(obj2.get("rt"));
        JSONObject obj4= JSONObject.fromObject(obj.get("userinfo"));
        System.out.println(obj.get("userinfo").toString());

    }
}