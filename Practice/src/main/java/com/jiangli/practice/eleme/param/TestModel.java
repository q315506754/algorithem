package com.jiangli.practice.eleme.param;

import com.jiangli.practice.eleme.core.Item;

import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/25 11:03
 */
public class TestModel {
    private List<Item> items5;

    public List<Item> getItems5() {
        return items5;
    }

    public void setItems5(List<Item> items5) {
        this.items5 = items5;
    }

    public TestModel(List<Item> items5) {
        this.items5 = items5;
    }

    public TestModel() {
    }
}
