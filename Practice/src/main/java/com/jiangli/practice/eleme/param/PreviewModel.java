package com.jiangli.practice.eleme.param;

import com.jiangli.practice.eleme.core.Item;

import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/25 11:03
 */
public class PreviewModel {
    private List<Item> items;
    private Integer merchantId;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public String toString() {
        return "PreviewModel{" +
                "items=" + items +
                ", merchantId=" + merchantId +
                '}';
    }
}
