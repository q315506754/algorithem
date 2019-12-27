package com.jiangli.springMVC;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;

/**
 * @author Jiangli
 * @date 2019/10/22 10:25
 */
public class DispatchDownloader {
    /**
     * 下载文件
     * /crm/downLoadFile
     */
    @RequestMapping(value = "/downloadFile")
    public void downLoadFile(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "fileUrl", required = false) String fileUrl, HttpServletResponse response) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        OutputStream output = null;
        try {
            response.setContentType("application/octet-stream; charset=UTF-8");
            String ext = fileUrl.substring(fileUrl.lastIndexOf("."));
            name = name + ext;
            name = URLDecoder.decode(name, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=\"" + new String(name.getBytes("GBK"), "ISO8859-1") + "\"");
            URL url = new URL(fileUrl);
            bis = new BufferedInputStream(url.openStream());
            output = response.getOutputStream();
            bos = new BufferedOutputStream(output);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
