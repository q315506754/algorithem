package com.jiangli.springMVCflow.model;

import org.springframework.stereotype.Component;

/**
 * @author Jiangli
 * @date 2018/12/29 18:51
 */
@Component("DetailsObj")
public class DetailsObj {
    Integer pageIndex;
    Integer pageSize;
    private String hotelId;
    private String hotelName;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

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

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }
}
