package com.jiangli.practice.eleme.core;

/**
 * Created by Jiangli on 2017/4/18.
 */
public class Item {
    private String  name;
    private Double  money;
    private Double  packageMoney;
    private int  num=1;

    public Double getPackageMoney() {
        return packageMoney;
    }

    public void setPackageMoney(Double packageMoney) {
        this.packageMoney = packageMoney;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (name != null ? !name.equals(item.name) : item.name != null) return false;
        if (money != null ? !money.equals(item.money) : item.money != null) return false;
        return packageMoney != null ? packageMoney.equals(item.packageMoney) : item.packageMoney == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (packageMoney != null ? packageMoney.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", money=" + money +
                ", packageMoney=" + packageMoney +
                ", num=" + num +
                '}';
    }
}
