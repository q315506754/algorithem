package com.jiangli.practice.eleme.core;

import com.jiangli.practice.eleme.model.Rule;

import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/18 13:37
 */
public class CalcContext {
    private Double  reduceForEachOrder=0d;
    private Integer  merchantId;
    private List<Rule> extraRules;
    private Cart cart;
    private Integer  minOrder=1;
    private Integer  maxOrder=3;

    public Double getReduceForEachOrder() {
        return reduceForEachOrder;
    }

    public void setReduceForEachOrder(Double reduceForEachOrder) {
        this.reduceForEachOrder = reduceForEachOrder;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public List<Rule> getExtraRules() {
        return extraRules;
    }

    public void setExtraRules(List<Rule> extraRules) {
        this.extraRules = extraRules;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Integer getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(Integer minOrder) {
        this.minOrder = minOrder;
    }

    public Integer getMaxOrder() {
        return maxOrder;
    }

    public void setMaxOrder(Integer maxOrder) {
        this.maxOrder = maxOrder;
    }
}
