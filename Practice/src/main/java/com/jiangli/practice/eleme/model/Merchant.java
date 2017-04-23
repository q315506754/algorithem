package com.jiangli.practice.eleme.model;

import javax.persistence.*;

/**
 * @author Jiangli
 * @date 2017/4/18 13:14
 */
@Entity
@Table(name="App.Merchant")
public class Merchant {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String  name;

    private Double  baseMoney=20d;

    private Double  distributionMoney=5d;

    private Integer likeit=0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Double getBaseMoney() {
        return baseMoney;
    }

    public void setBaseMoney(Double baseMoney) {
        this.baseMoney = baseMoney;
    }

    public Double getDistributionMoney() {
        return distributionMoney;
    }

    public void setDistributionMoney(Double distributionMoney) {
        this.distributionMoney = distributionMoney;
    }

    public Integer getLikeit() {
        return likeit;
    }

    public void setLikeit(Integer likeit) {
        this.likeit = likeit;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", baseMoney=" + baseMoney +
                ", distributionMoney=" + distributionMoney +
                ", likeit=" + likeit +
                '}';
    }
}
