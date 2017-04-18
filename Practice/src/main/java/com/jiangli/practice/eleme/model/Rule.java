package com.jiangli.practice.eleme.model;

import javax.persistence.*;

/**
 * @author Jiangli
 * @date 2017/4/18 13:14
 */
@Entity
@Table(name="App.Rule")
public class Rule {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MERCHANTID")
    private Integer merchantId;

    @Column(name = "REACH")
    private Double  reach;

    @Column(name = "REDUCE")
    private Double  reduce;

    @Column(name = "SORT")
    private Integer sort;


    @Override
    public String toString() {
        return "Rule{" +
                "id=" + id +
                ", merchantId=" + merchantId +
                ", reach=" + reach +
                ", reduce=" + reduce +
                ", order=" + sort +
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
