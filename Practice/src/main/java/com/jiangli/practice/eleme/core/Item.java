package com.jiangli.practice.eleme.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiangli on 2017/4/18.
 */
public class Item {
    private String  name;
    private Double  money;
    private List<Double> extraMoney=new ArrayList<>();
    private int  num=1;

    public List<Double> getExtraMoney() {
        return extraMoney;
    }

    public void setExtraMoney(List<Double> extraMoney) {
        this.extraMoney = extraMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
