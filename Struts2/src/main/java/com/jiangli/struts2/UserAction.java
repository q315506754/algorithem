package com.jiangli.struts2;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by Jiangli on 2016/9/1.
 */
public class UserAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    public String execute(){
        return SUCCESS;
    }

    public String login() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            System.out.println("name->" + username + ",password->"
                    + password);
            if ("admin".equals(username) && "123456".equals(password)) {
                return SUCCESS;
            } else {
                return "login";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }
}

