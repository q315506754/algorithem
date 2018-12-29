package com.jiangli.springMVCflow.model;

import java.io.Serializable;

/**
 * @author Jiangli
 * @date 2018/12/29 18:58
 */
public class ViewObject implements Serializable{
    Integer pageIndex;
    Integer pageSize;
    private String hotelName;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    @Override
    public String toString() {
        return "ViewObject{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", hotelName='" + hotelName + '\'' +
                '}';
    }
}
