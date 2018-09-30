package com.jiangli.common.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Jiangli
 * @date 2018/9/30 9:09
 */
public class EmailUtil {

    public  static class EmailConfig{
        public static final String FROM_ADDRESS = "315506754@qq.com";
        public static final String FROM_PWD = "ddekhnzuwlzybjhe";
    }

    /**
    <dependency>
     <groupId>javax.mail</groupId>
     <artifactId>mail</artifactId>
     <version>1.4.7</version>
     </dependency>
     */
    public  static boolean sendByJavaMail(String title,String toAddress,String content){
        String[] split = toAddress.split(",");

// 1.创建一个程序与邮件服务器会话对象 Session
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "SMTP");
        props.setProperty("mail.smtp.host", "smtp.qq.com");
        props.setProperty("mail.smtp.port", "25");
        //props.setProperty("mail.smtp.port", "465");

        // 指定验证为true
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.timeout","3000");
        // 验证账号及密码，密码需要是第三方授权码
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(EmailConfig.FROM_ADDRESS, EmailConfig.FROM_PWD);
            }
        };
        Session session = Session.getInstance(props, auth);

// 2.创建一个Message，它相当于是邮件内容
        Message message = new MimeMessage(session);
// 设置发送者
        try {
            message.setFrom(new InternetAddress(EmailConfig.FROM_ADDRESS));
            //message.setFrom(new InternetAddress("***@qq.com"));

            // 设置发送方式与接收者
            //message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toAddress));
            //message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toAddress+","+toAddress+",jiangli@able-elec.com"));
            //message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress("***@qq.com"));
            InternetAddress[] addresses = new InternetAddress[split.length];
            int count = 0;
            for (String s : split) {
                addresses[count++] = new InternetAddress(s);
            }

            //抄送
            message.addRecipients(MimeMessage.RecipientType.TO,addresses);

// 设置主题
            message.setSubject(title);
// 设置内容
            message.setContent(content, "text/html;charset=utf-8");

// 3.创建 Transport用于将邮件发送
            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        sendByJavaMail("问答广告内容检测", "315506754@qq.com", "2018-09-30 09:11:06 ZHS_BBS.QA_ANSWER CONTINUOUS_NUMBER 1271025 187916017 1252784899@qq.com 感谢！！<br/>2018-09-30 09:11:06 ZHS_BBS.QA_ANSWER CONTINUOUS_NUMBER 1271025 187916017 1252784899@qq.com 感谢！！");
    }
}
