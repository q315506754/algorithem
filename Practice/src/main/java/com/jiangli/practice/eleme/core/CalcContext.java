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
    private String  queryId;
    private Integer  merchantId;
    private Boolean  isVip=false;
    private List<Rule> redEnvelope;
    private Cart cart;
    private Integer  minOrder=1;
    private Integer  maxOrder=3;
    private Integer  maxRedEnvelopeChosen=2;

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

    public List<Rule> getRedEnvelope() {
        return redEnvelope;
    }

    public void setRedEnvelope(List<Rule> redEnvelope) {
        this.redEnvelope = redEnvelope;
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

    public void setIsVip(Boolean vip) {
        isVip = vip;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public Integer getMaxRedEnvelopeChosen() {
        return maxRedEnvelopeChosen;
    }

    public void setMaxRedEnvelopeChosen(Integer maxRedEnvelopeChosen) {
        this.maxRedEnvelopeChosen = maxRedEnvelopeChosen;
    }


    @Override
    public String toString() {
        return "CalcContext{" +
                "extraMoneyForEachOrder=" + extraMoneyForEachOrder +
                ", queryId='" + queryId + '\'' +
                ", merchantId=" + merchantId +
                ", isVip=" + isVip +
                ", redEnvelope=" + redEnvelope +
                ", cart=" + cart +
                ", minOrder=" + minOrder +
                ", maxOrder=" + maxOrder +
                ", maxRedEnvelopeChosen=" + maxRedEnvelopeChosen +
                ", solutions=" + solutions +
                '}';
    }
}
