package com.jiangli.practice.eleme.core;

import com.jiangli.common.utils.ArrayUtil;
import com.jiangli.common.utils.CollectionUtil;
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

    public double calcTotalMoney(List<Dish> selectedDishes) {
        double ret = 0d;
        for (Dish selectedDish : selectedDishes) {
            ret+=selectedDish.getMoney();
            ret+=selectedDish.getPackageMoney();
        }
        return ret;
    }

    public void calc(CalcContext context) {
        Integer merchantId = context.getMerchantId();
        Merchant merchant=merchantRespository.findOne(merchantId);
//        List<Dish> selectedDishes=dishRespository.findByMerchantId(merchantId);
        List<Dish> selectedDishes=convertToDish(context);
        double calcTotalMoney = calcTotalMoney(selectedDishes);

        List<Rule> rules=ruleRespository.findByMerchantIdOrderBySortAsc(merchantId);
        Collections.reverse(rules);

        logger.debug("merchant:"+merchant);
        logger.debug("selectedDishes:"+selectedDishes);
        logger.debug("rules:"+rules);

        List<Solution> solutions = context.getSolutions();
        for (int i = context.getMinOrder(); i<=context.getMaxOrder(); i++) {
            logger.debug("ordernum:{} maxorder:{}",i,context.getMaxOrder());

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
            if (calcTotalMoney<i*merchant.getDistributionMoney()) {
                Solution cur = new Solution();
                cur.setOrderNum(i);
                cur.setOrderNum(i);
                cur.setFailed(true);
                cur.setFailedReason("无法满足起送条件");
                solutions.add(cur);
                continue;
            }
            int redEnvelopSize = CollectionUtil.size(context.getRedEnvelope());
            OrderDistributor distributor = new OrderDistributor(i, ArrayUtil.newArray(size,0));
            int _expectedOrder = CollectionUtil.sizeIter(distributor);
            int _expectedRedEnvelope = CollectionUtil.sizeIter(new ArrangementSupport(i,redEnvelopSize));
            int _expectedLoop=_expectedOrder*_expectedRedEnvelope;
            logger.debug("expectedOrder:{}",_expectedOrder);
            logger.debug("expectedRedEnvelope:{}",_expectedRedEnvelope);
            logger.debug("expectedLoop:{}",_expectedLoop);

            Solution minSolution=null;

            //各订单已经分好dish[[],[],[]]
            for (int[][] ints : distributor) {

                //迭代每一种红包使用方法
                ArrangementSupport redEnvelopDistributor = new ArrangementSupport(i,redEnvelopSize);

                for (int[] redEnvelopIdxForList : redEnvelopDistributor) {
                    Solution cur = new Solution();
                    cur.setOrderNum(i);

                    //生成每一个订单
                    for (int orderNum = 0; orderNum < ints.length; orderNum++) {
                        Order one = new Order();
                        int envelopIdxForOrder = redEnvelopIdxForList[orderNum];
                        Rule redEnvelop = CollectionUtil.get(context.getRedEnvelope(), envelopIdxForOrder);

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
                        Double activityReduce=null;
                        String activityReduceString=null;
                        for (Rule rule : rules) {
                            if (priceTotal >= rule.getReach()) {
                                activityReduce = rule.getReduce();
                                activityReduceString="在线支付立减优惠,满"+rule.getReach()+"减"+activityReduce;
                                break;
                            }
                        }

                        double priceAfterReduce = priceTotal;
                        if (activityReduce!=null) {
                            one.addReducedMoney(activityReduceString,activityReduce);
                        }

                        //红包减免
                        Double redEnvelopReduce=null;
                        String redEnvelopReduceString=null;

                        if (redEnvelop!=null) {
                            //使用红包
                            if (priceTotal >= redEnvelop.getReach()) {
                                redEnvelopReduce = redEnvelop.getReduce();
                                redEnvelopReduceString="使用红包,满"+redEnvelop.getReach()+"减"+redEnvelopReduce;
                            }
                        }

                        if (redEnvelopReduce!=null) {
                            one.addReducedMoney(redEnvelopReduceString,redEnvelopReduce);
                        }


                        //reduce
                        if (activityReduce!=null) {
                            priceAfterReduce-=activityReduce;
                        }
                        if (redEnvelopReduce!=null) {
                            priceAfterReduce-=redEnvelopReduce;
                        }

                        double priceFinal = priceAfterReduce;

                        //加上配送
                        Double distributionMoney = merchant.getDistributionMoney();
                        priceFinal+=distributionMoney;
                        one.addExtraMoney("配送费",distributionMoney);

                        //会员减免
                        if (context.getVip()) {
                            double vipReduce = 4d;
                            one.addReducedMoney("会员减免配送费", vipReduce);
                            priceFinal-= vipReduce;
                        }

                        //set rs
                        one.setPrice(priceFinal);

                        cur.addOrder(one);
                    }

                    if (cur!=null && (minSolution==null ||  cur.getPrice()<minSolution.getPrice())) {
                        minSolution=cur;
                    }
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
