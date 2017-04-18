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

    @Override
    public String toString() {
        return "Merchant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
