package com.jiangli.db.model;

import java.util.Date;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-01-24 13:37
 */
public class UserLog {

    private Long userId;
    private UserLogType type;
    private Date ctime;
    private String remark;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserLogType getType() {
        return type;
    }

    public void setType(UserLogType type) {
        this.type = type;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
