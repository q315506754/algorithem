package com.jiangli.practice.eleme.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/19 13:29
 */
public class Solution {
    private Integer  orderNum;
    private List<Order> orders;
    private Double price=0d;
    private boolean failed=false;
    private String failedReason;

    public static  Solution newFailed(int orderNum,String reason){
        Solution cur = new Solution();
        cur.setOrderNum(orderNum);
        cur.setFailed(true);
        cur.setFailedReason(reason);
        return cur;
    }
    @Override
    public String toString() {
        return "Solution{" +
                "orderNum=" + orderNum +
                ", orders=" + orders +
                ", price=" + price +
                ", failed=" + failed +
                ", failedReason='" + failedReason + '\'' +
                '}';
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
        this.orders = new ArrayList<>(orderNum);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        this.price += order.getPrice();
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

}
