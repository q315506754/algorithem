package com.jiangli.db.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-01-19 15:34
 */
@Table(name="TBL_USER")
public class User {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id ;           // 用户ID
    private String areaCode ;   // 国际区号，默认：中国大陆 （+86）
    private String mobile;      // 手机号
    private String email;       // 邮箱
    private String password;    // 密码，SHA-256加密
    private String name;        // 用户姓名
    private String avatar;      // 用户头像
    private Date createTime;   // 用户注册时间
    private Integer isDeleted;  // 删除标识
    private Integer isDisabled; // 禁用标识


    private int pageNumKey=1;
    private int pageSizeKey=10;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", areaCode='" + areaCode + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", isDeleted=" + isDeleted +
                ", isDisabled=" + isDisabled +
                ", pageNumKey=" + pageNumKey +
                ", pageSizeKey=" + pageSizeKey +
                '}';
    }

    public int getPageNumKey() {
        return pageNumKey;
    }

    public void setPageNumKey(int pageNumKey) {
        this.pageNumKey = pageNumKey;
    }

    public int getPageSizeKey() {
        return pageSizeKey;
    }

    public void setPageSizeKey(int pageSizeKey) {
        this.pageSizeKey = pageSizeKey;
    }
}
