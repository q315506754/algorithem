package com.jiangli.practice.eleme.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/19 13:29
 */
public class Order {
    private List<ReducedMoney> reducedMoneyList=new ArrayList<>();
    private List<ExtraMoney> extraMoneyList=new ArrayList<>();
    private List<Item> items=new ArrayList<>();
    private Double price=0d;

    class ReducedMoney{
        private String name;
        private Double price;

        public ReducedMoney(String name, Double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "ReducedMoney{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
    class ExtraMoney{
        private String name;
        private Double price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public ExtraMoney(String name, Double price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return "ExtraMoney{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }
    }

    public List<ReducedMoney> getReducedMoneyList() {
        return reducedMoneyList;
    }

    public void setReducedMoneyList(List<ReducedMoney> reducedMoneyList) {
        this.reducedMoneyList = reducedMoneyList;
    }

    public List<ExtraMoney> getExtraMoneyList() {
        return extraMoneyList;
    }

    public void setExtraMoneyList(List<ExtraMoney> extraMoneyList) {
        this.extraMoneyList = extraMoneyList;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void addReducedMoney(String name,Double money) {
        this.reducedMoneyList.add(new ReducedMoney(name,money));
    }
    public void addExtraMoney(String name,Double money) {
        this.extraMoneyList.add(new ExtraMoney(name,money));
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "reducedMoneyList=" + reducedMoneyList +
                ", extraMoneyList=" + extraMoneyList +
                ", items=" + items +
                ", price=" + price +
                '}';
    }
}
