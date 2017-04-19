package com.jiangli.practice.eleme.core;

import com.jiangli.practice.eleme.model.Rule;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/18 13:37
 */
public class CalcContext {
    private Double extraMoneyForEachOrder =0d;
    private Integer  merchantId;
    private Boolean  isVip=true;
    private List<Rule> extraRules;
    private Cart cart;
    private Integer  minOrder=1;
    private Integer  maxOrder=3;
    private final List<Solution> solutions=new LinkedList<>();

    public List<Solution> getSolutions() {
        return solutions;
    }

    public Double getExtraMoneyForEachOrder() {
        return extraMoneyForEachOrder;
    }

    public void setExtraMoneyForEachOrder(Double extraMoneyForEachOrder) {
        this.extraMoneyForEachOrder = extraMoneyForEachOrder;
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

    public Boolean getVip() {
        return isVip;
    }

    public void setVip(Boolean vip) {
        isVip = vip;
    }
}
