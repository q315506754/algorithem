package com.jiangli.springMVC.utils.iterutil;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Jiangli
 * @date 2020/6/6 10:56
 */
public class ShowDocUtil {
    public static void createShowDocAndPrint(ShowDocDto showDocDto)  {
        try {
            String content = createShowDocTxt(showDocDto);

            File tempFile = File.createTempFile(System.currentTimeMillis() + "-" + UUID.randomUUID(), ".txt");
            //
            IOUtils.write(content,new FileOutputStream(tempFile));
            System.out.println("show doc : file:"+tempFile.getAbsolutePath());
            System.out.println(tempFile.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String createShowDocTxt(ShowDocDto showDocDto) {
        String paramStr = "";
        for (MethoParam method : showDocDto.methods) {
            paramStr = paramStr + "|"+method.reqQame+" |"+(method.isMust?"是":"否")+"  |"+method.type+" |"+method.remark+"   |\n";
        }

        return "    \n" +
                "**简要描述：** \n" +
                "\n" +
                "- " +showDocDto.desc +
                "\n\n" +
                "**请求URL：** \n" +
                "- ` "+showDocDto.url+" `\n" +
                "- ` "+showDocDto.yu_url+" `\n" +
                "- ` "+showDocDto.local_url+" `\n" +
                "  \n" +
                "**请求方式：**\n" +
                "- " +showDocDto.type +
                "\n\n" +
                "**参数：** \n" +
                "\n" +
                "|参数名|必选|类型|说明|\n" +
                "|:----    |:---|:----- |-----   |\n" +
                paramStr +
                "\n" +
                " **返回示例**\n" +
                "\n" +
                "``` \n" +
                "{\n" +
                "    \"status\": \"SUCCESS\",\n" +
                "    \"message\": null,\n" +
                "    \"rt\": {\n" +
                "        \"title\": \"陈春花的回放\",\n" +
                "        \"videoUrl\": \"http://www.youtube.com/1234.mp4\",\n" +
                "        \"videoId\": \"234\",\n" +
                "        \"cover\": \"http://image.g2s.cn/zhs_yanfa_150820/ablecommons/demo/201805/713ff29044174b74b8441803f440f8db.jpeg\",\n" +
                "        \"shareUrl\": \"http://image.g2s.cn/1111.html\",\n" +
                "        \"watchNum\": 1234\n" +
                "    },\n" +
                "    \"successful\": true,\n" +
                "    \"currentTime\": 1529915077780\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "\n" +
                "\n" +
                " **备注** \n" +
                "\n" +
                "- 无 \n" +
                "\n" +
                "\n";
    }
}
