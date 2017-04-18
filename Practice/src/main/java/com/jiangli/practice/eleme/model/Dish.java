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
    private Integer merchantId;
    private String  name;
    private Double  money;

    @Column(name = "package")
    private Double  packageMoney=0d;


    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", merchantId=" + merchantId +
                ", name='" + name + '\'' +
                ", money=" + money +
                ", packageMoney=" + packageMoney +
                '}';
    }

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

    public void setPackageMoney(Double packageMoney) {
        this.packageMoney = packageMoney;
    }
}
