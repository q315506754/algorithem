package com.jiangli;

import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * @author Jiangli
 * @date 2017/4/7 17:30
 */
public class DownloadClass {
    public void downloadPic(final String url, HttpServletRequest request, HttpServletResponse response) {
//        String displayName = "asd打算犯法的事asd.jpg";
        String displayName = url.substring(url.lastIndexOf("/")+1);


        //文字转码
        try {
            String userAgent = request.getHeader("User-Agent");
            boolean isIE = (userAgent != null) && (userAgent.toLowerCase().indexOf("msie") != -1);
            if (isIE) {
                displayName = URLEncoder.encode(displayName, "UTF-8");
                displayName = "\"" + displayName + "\"";
            } else {
                displayName = new String(displayName.getBytes("UTF-8"), "ISO8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("image/*;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + displayName);

        //传输
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            // 构造URL
            URL uRL = new URL(url);
            // 打开连接
            URLConnection con = uRL.openConnection();
            //设置请求超时为10s
            con.setConnectTimeout(10*1000);

            // 输入流
            InputStream is = con.getInputStream();
            os = new BufferedOutputStream(response.getOutputStream());
            bis = new BufferedInputStream(is);
            FileCopyUtils.copy(bis, os);
            //IOUtils.copy(is, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bis != null)
                    bis.close();
                if(os != null)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
