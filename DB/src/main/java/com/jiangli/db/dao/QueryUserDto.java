package com.jiangli.db.dao;

/**
 * @author Jiangli
 * @date 2018/12/19 10:56
 */
public class QueryUserDto {
    private int isDeleted;
    private int pageNumKey;
    private int pageSizeKey;

    @Override
    public String toString() {
        return "QueryUserDto{" +
                "isDeleted=" + isDeleted +
                ", pageNumKey=" + pageNumKey +
                ", pageSizeKey=" + pageSizeKey +
                '}';
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
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
