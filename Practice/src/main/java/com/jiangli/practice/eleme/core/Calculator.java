package com.jiangli.practice.eleme.core;

import com.jiangli.common.utils.ArrayUtil;
import com.jiangli.common.utils.MethodUtil;
import com.jiangli.practice.eleme.dao.DishRespository;
import com.jiangli.practice.eleme.dao.MerchantRespository;
import com.jiangli.practice.eleme.dao.RuleRespository;
import com.jiangli.practice.eleme.model.Dish;
import com.jiangli.practice.eleme.model.Merchant;
import com.jiangli.practice.eleme.model.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2017/4/18 13:35
 */
@Component
public class Calculator {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DishRespository dishRespository;

    @Autowired
    private MerchantRespository merchantRespository;

    @Autowired
    private RuleRespository ruleRespository;

    public List<Dish>  convertToDish(CalcContext context) {
        List<Dish> ret = new LinkedList<>();
        Cart cart = context.getCart();
        List<Item> items = cart.getItems();
        for (Item item : items) {
            int num = item.getNum();
            while (num-->0) {
                Dish one = new Dish();
                MethodUtil.copyProp(item,"name",one);
                MethodUtil.copyProp(item,"money",one);
                MethodUtil.copyProp(item,"packageMoney",one);
                ret.add(one);
            }
        }
        return ret;
    }

    public void calc(CalcContext context) {
        Integer merchantId = context.getMerchantId();
        Merchant merchant=merchantRespository.findOne(merchantId);
//        List<Dish> selectedDishes=dishRespository.findByMerchantId(merchantId);
        List<Dish> selectedDishes=convertToDish(context);
        List<Rule> rules=ruleRespository.findByMerchantIdOrderBySortAsc(merchantId);
        Collections.reverse(rules);

        logger.debug("merchant:"+merchant);
        logger.debug("selectedDishes:"+selectedDishes);
        logger.debug("rules:"+rules);

        List<Solution> solutions = context.getSolutions();
        for (int i = context.getMinOrder(); i<=context.getMaxOrder(); i++) {
            int size = selectedDishes.size();
            if (i>size) {
                Solution cur = new Solution();
                cur.setOrderNum(i);
                cur.setOrderNum(i);
                cur.setFailed(true);
                cur.setFailedReason("无法分配至该订单数目");
                solutions.add(cur);
                continue;
            }

            OrderDistributor distributor = new OrderDistributor(i, ArrayUtil.newArray(size,0));
            Solution minSolution=null;

            for (int[][] ints : distributor) {
                Solution cur = new Solution();
                cur.setOrderNum(i);

                for (int orderNum = 0; orderNum < ints.length; orderNum++) {
                    Order one = new Order();

                    int[] dishes =  ints[orderNum];
                    //菜总价
                    double priceForDish = 0d;
                    //打包费
                    double priceForPackage = 0d;

                    for (int dishOrd : dishes) {
                        Dish dish = selectedDishes.get(dishOrd);
                        priceForDish+=dish.getMoney();
                        priceForPackage+=dish.getPackageMoney();

                        Item item = new Item();
                        MethodUtil.copyProp(dish,"name",item);
                        MethodUtil.copyProp(dish,"money",item);
                        MethodUtil.copyProp(dish,"packageMoney",item);
                        one.addItem(item);
                    }

                    double priceTotal = priceForDish+priceForPackage;
                    one.addExtraMoney("一般",priceForDish);
                    one.addExtraMoney("餐盒",priceForPackage);
                    one.addExtraMoney("合计",priceTotal);

                    //reach min?
                    if(priceTotal<merchant.getBaseMoney()){
                        //if not
                        //该Solution作废
                        cur = null;
                        break;
                    }

                    //满x减y
                    Double reduceMoney=null;
                    String reduceString=null;
                    for (Rule rule : rules) {
                        if (priceTotal >= rule.getReach()) {
                            reduceMoney = rule.getReduce();
                            reduceString="在线支付立减优惠,满"+rule.getReach()+"减"+reduceMoney;
                            break;
                        }
                    }

                    double priceAfterReduce = priceTotal;
                    if (reduceMoney!=null) {
                        one.addReducedMoney(reduceString,reduceMoney);

                        priceAfterReduce-=reduceMoney;
                    }

                    //加上配送
                    one.addExtraMoney("配送费",5d);
                    one.addReducedMoney("会员减免配送费",4d);
                    double priceFinal = priceAfterReduce+context.getExtraMoneyForEachOrder();
                    one.setPrice(priceFinal);

                    cur.addOrder(one);
                }

                if (cur!=null && (minSolution==null ||  cur.getPrice()<minSolution.getPrice())) {
                    minSolution=cur;
                }
            }

            if(minSolution==null){
                minSolution = new Solution();
                minSolution.setOrderNum(i);
                minSolution.setFailed(true);
                minSolution.setFailedReason("无法满足起送条件");
            }

            solutions.add(minSolution);

        }

        //merge item
        for (Solution solution : solutions) {
            if (!solution.isFailed()) {
                List<Order> orders = solution.getOrders();
                for (Order order : orders) {
                    order.setItems(mergeItem(order.getItems()));
                }
            }
        }
    }

    private List<Item> mergeItem(List<Item> items){
        List<Item> ret = new ArrayList<>(items.size());
        for (Item item : items) {
            if (ret.contains(item)) {
                for (Item i : ret) {
                    if (i.equals(item)) {
                        i.setNum(i.getNum()+1);
                        break;
                    }
                }
            }else {
                ret.add(item);
            }
        }
        return ret;
    }
}
