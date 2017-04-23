package com.jiangli.practice.eleme.model;

import javax.persistence.*;

/**
 * @author Jiangli
 * @date 2017/4/18 13:14
 */
@Entity
@Table(name="App.Dish")
public class Dish {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "merchantId")
    private Integer merchantId;

    @Column(name = "name")
    private String  name;

    @Column(name = "money")
    private Double  money;

    @Column(name = "package")
    private Double  packageMoney=0d;

    @Column(name = "likeit")
    private Integer likeit=0;

    @Column(name = "times")
    private Integer times=0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
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

    public Double getPackageMoney() {
        return packageMoney;
    }

    public Integer getLikeit() {
        return likeit;
    }

    public void setLikeit(Integer likeit) {
        this.likeit = likeit;
    }

    public void setPackageMoney(Double packageMoney) {


        this.packageMoney = packageMoney;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", merchantId=" + merchantId +
                ", name='" + name + '\'' +
                ", money=" + money +
                ", packageMoney=" + packageMoney +
                ", likeit=" + likeit +
                ", times=" + times +
                '}';
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }


}
