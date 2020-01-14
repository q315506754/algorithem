package com.jiangli.capcha;

import com.jiangli.common.utils.FileUtil;
import net.marketer.RuCaptcha;

import java.io.File;

/**
 * @author Jiangli
 * @date 2020/1/10 14:24
 */
public class CapchaMain {

    public static void main(String[] args) {
        File imgFile = FileUtil.getClassPathFile("imgs/aaa.jpg");
        System.out.println(imgFile.exists());
        //验证码图像的路径
        //File imgFile = new File("путь к изображению капчи");
        RuCaptcha.API_KEY = "5304564fecca0466dadbaa8e595597de";
        String CAPCHA_ID;
        String decryption = null;

        String response = null;
        try {
            response = RuCaptcha.postCaptcha(imgFile);
            System.out.println(response);

            if (response.startsWith("OK")) {
                CAPCHA_ID = response.substring(3);

                while (true){
                    response = RuCaptcha.getDecryption(CAPCHA_ID);
                    if(response.equals(RuCaptcha.Responses.CAPCHA_NOT_READY.toString())){
                        Thread.sleep(5000);
                        continue;
                    }else if(response.startsWith("OK")){
                        decryption = response.substring(3);
                        break;
                    }else {
                        //
                        //错误处理
                    }
                }
                //
                //您的代码以使用生成的验证码解密
            }


            System.out.println(decryption);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
