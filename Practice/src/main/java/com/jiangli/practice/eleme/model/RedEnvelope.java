package com.jiangli.practice.eleme.model;

import javax.persistence.*;

/**
 * @author Jiangli
 * @date 2017/4/18 13:14
 */
@Entity
@Table(name="App.RedEnvelope")
public class RedEnvelope {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "REACH")
    private Double  reach;

    @Column(name = "REDUCE")
    private Double  reduce;

    private Boolean  isEnable=true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Double getReach() {
        return reach;
    }

    public void setReach(Double reach) {
        this.reach = reach;
    }

    public Double getReduce() {
        return reduce;
    }

    public void setReduce(Double reduce) {
        this.reduce = reduce;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    @Override
    public String toString() {
        return "RedEnvelope{" +
                "id=" + id +
                ", reach=" + reach +
                ", reduce=" + reduce +
                ", isEnable=" + isEnable +
                '}';
    }
}
