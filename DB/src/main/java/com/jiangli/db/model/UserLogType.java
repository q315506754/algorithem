package com.jiangli.db.model;

/**
 * 用户日志类型
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-01-24 13:31
 */
public enum UserLogType {

    CREATE(1),
    UPDATE(2),
    DISABLE(3),
    ENABLE(4),
    DELETE(5),
    ;

    private int value;

    private UserLogType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * 通过值获取枚举实例
     * @param value
     * @return
     */
    public static final UserLogType valueOf(int value) {
        for (UserLogType type : UserLogType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }

}
