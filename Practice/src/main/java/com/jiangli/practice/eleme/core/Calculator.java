package com.jiangli.practice.eleme.core;

import com.jiangli.common.core.ThreadCollector;
import com.jiangli.common.utils.*;
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
    private boolean cancelled=false;

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

    public static class QueryDetail {
         List<Solution> solutions;
         int curOrder;
         int maxOrder;

        public List<Solution> getSolutions() {
            return solutions;
        }

        public void setSolutions(List<Solution> solutions) {
            this.solutions = solutions;
        }

        public int getCurOrder() {
            return curOrder;
        }

        public void setCurOrder(int curOrder) {
            this.curOrder = curOrder;
        }

        public int getMaxOrder() {
            return maxOrder;
        }

        public void setMaxOrder(int maxOrder) {
            this.maxOrder = maxOrder;
        }

        @Override
        public String toString() {
            return "QueryDetail{" +
                    "solutions=" + solutions +
                    ", curOrder=" + curOrder +
                    ", maxOrder=" + maxOrder +
                    '}';
        }
    }
    public void calc(CalcContext context) {
        ThreadCollector.registerCancelListener(context.getQueryId(),()->{
            cancelled =true;
        } );
        QueryDetail queryDetail = new QueryDetail();
        queryDetail.maxOrder=context.getMaxOrder();
        queryDetail.solutions = context.getSolutions();

        ThreadCollector.RunningStatistics<QueryDetail> statistics = ThreadCollector.start(context.getQueryId());
        statistics.setDetail(queryDetail);

        Integer merchantId = context.getMerchantId();
        Merchant merchant=merchantRespository.findOne(merchantId);
        List<Dish> selectedDishes=convertToDish(context);
        final double selectedDishesTotalMoney = calcTotalMoney(selectedDishes);

        List<Rule> rules=ruleRespository.findByMerchantIdOrderBySortAsc(merchantId);
        Collections.reverse(rules);

        List<Rule> redEnvelopeCandicates = context.getRedEnvelope();

        logger.debug("merchant:"+merchant);
        logger.debug("selectedDishes:"+selectedDishes);
        logger.debug("rules:"+rules);

        List<Solution> solutions = context.getSolutions();
        for ( int i = context.getMinOrder(); i<=context.getMaxOrder(); i++) {
            final int I = i;
            logger.debug("ordernum:{} maxorder:{}",i,context.getMaxOrder());

            int selectedDishesSize = selectedDishes.size();
            if (i>selectedDishesSize) {
                solutions.add(Solution.newFailed(i,"无法分配至该订单数目"));
                continue;
            }
            if (selectedDishesTotalMoney<i*merchant.getDistributionMoney()) {
                solutions.add(Solution.newFailed(i,"无法满足起送条件"));
                continue;
            }

            OrderDistributor distributor = new OrderDistributor(i, ArrayUtil.newArray(selectedDishesSize,0));
            Solution minSolution=null;

            int redEnvelopSize = CollectionUtil.size(redEnvelopeCandicates);
            int _expectedOrder = CollectionUtil.sizeIter(distributor);
            int _expectedRedEnvelope = CollectionUtil.sizeIter(new LimittedArrangementSupport(i,redEnvelopSize,context.getMaxRedEnvelopeChosen()));
            int _expectedLoop=_expectedOrder*_expectedRedEnvelope*i;

            SpeedRecorder speedRecorder = SpeedRecorder.build();
            speedRecorder.setInterval(a->{
                logger.debug("{}:estimate time:{}  {} _expectedLoop:{}/count:{} speed:{}loop/sec",a,speedRecorder.estimateRestTime(_expectedLoop), NumberUtil.getPercentString(speedRecorder.getCount(),_expectedLoop),_expectedLoop,speedRecorder.getCount(),speedRecorder.averageSpeedString(3));
                statistics.setCurrent(speedRecorder.getCount());
                statistics.setTotal(_expectedLoop);
                statistics.setPercent(NumberUtil.getPercentString(speedRecorder.getCount(),_expectedLoop));
                statistics.setRestTime(speedRecorder.estimateRestTime(_expectedLoop));

                queryDetail.curOrder= I;
            },3000);
            logger.debug("expectedOrder:{}",_expectedOrder);
            logger.debug("expectedRedEnvelope:{}",_expectedRedEnvelope);
            logger.debug("expectedLoop:{}",_expectedLoop);

            //各订单已经分好dish[[],[],[]]
            for (int[][] orderAndDishIdx : distributor) {

                //迭代每一种红包使用方法
                //红包使用量受限 default:max=2
                LimittedArrangementSupport redEnvelopDistributor = new LimittedArrangementSupport(i,redEnvelopSize,context.getMaxRedEnvelopeChosen());

                for (int[] redEnvelopIdxForList : redEnvelopDistributor) {
                    Solution cur = new Solution();
                    cur.setOrderNum(i);

                    //生成每一个订单 orderAndDishIdx.length=i
                    for (int orderNum = 0; orderNum < i; orderNum++) {
                        speedRecorder.record();

                        //中断
                        if (cancelled) {
                            for (int restOrderIdx = i; restOrderIdx < context.getMaxOrder(); restOrderIdx++) {
                                solutions.add(Solution.newFailed(i,"查询已被取消"));
                            }
                            return;
                        }

                        Order one = new Order();

                        //菜总价
                        double priceForDish = 0d;
                        //打包费
                        double priceForPackage = 0d;

                        for (int dishOrd : orderAndDishIdx[orderNum]) {
                            Dish dish = selectedDishes.get(dishOrd);
                            priceForDish+=dish.getMoney();
                            priceForPackage+=dish.getPackageMoney();

                            Item item = new Item();
                            MethodUtil.copyProp(dish,"name",item);
                            MethodUtil.copyProp(dish,"money",item);
                            MethodUtil.copyProp(dish,"packageMoney",item);
                            one.addItem(item);
                        }

                        double priceBase = priceForDish+priceForPackage;
                        one.addExtraMoney("一般",priceForDish);
                        one.addExtraMoney("餐盒",priceForPackage);
                        one.addExtraMoney("合计",priceBase);
                        double priceFinal=priceBase;

                        //reach min?起送费
                        if(priceBase<merchant.getBaseMoney()){
                            //if not
                            //该Solution作废
                            cur = null;

                            //剩下的需要记录
                            for (int rest = orderNum + 1; rest < i; rest++) {
                                speedRecorder.record();
                            }
                            break;
                        }

                        //满x减y
                        Rule activityReduceRule = activityReduce(rules, priceBase);
                        if (activityReduceRule!=null) {
                            Double activityReduce=activityReduceRule.getReduce();
                            one.addReducedMoney("在线支付立减优惠,满"+activityReduceRule.getReach()+"减"+activityReduce,activityReduce);
                            priceFinal = calcReduce(priceFinal, activityReduce);
                        }


                        //红包减免
                        Rule redEnvelop = CollectionUtil.get(redEnvelopeCandicates, redEnvelopIdxForList[orderNum]);
                        Double redEnvelopReduce = redEnvelopReduce(redEnvelop, priceBase);
                        if (redEnvelopReduce!=null) {
                            one.addReducedMoney("使用红包,满"+redEnvelop.getReach()+"减"+redEnvelopReduce,redEnvelopReduce);
                            priceFinal = calcReduce(priceFinal, redEnvelopReduce);
                        }


                        //加上配送
                        Double distributionMoney = merchant.getDistributionMoney();
                        priceFinal+=distributionMoney;
                        one.addExtraMoney("配送费",distributionMoney);

                        //会员减免
                        if (context.getVip()) {
                            double vipReduce = 4d;
                            one.addReducedMoney("会员减免配送费", vipReduce);
                            priceFinal = calcReduce(priceFinal, vipReduce);
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
                minSolution=Solution.newFailed(i,"无法满足起送条件");
            }

            solutions.add(minSolution);

            logger.debug("_expectedLoop:{} realLoop:{}",_expectedLoop,speedRecorder.getCount());
            logger.debug("i:{} minSolution:{}",i,minSolution);
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

        ThreadCollector.finish(context.getQueryId());
    }

    private Rule activityReduce(List<Rule> rules, double priceBase) {
        for (Rule rule : rules) {
            Double aDouble = redEnvelopReduce(rule, priceBase);
            if (aDouble!=null) {
                return rule;
            }
        }
        return null;
    }

    private Double redEnvelopReduce(Rule redEnvelop, double priceBase) {
        Double redEnvelopReduce=null;

        if (redEnvelop!=null) {
            //使用红包
            if (priceBase >= redEnvelop.getReach()) {
                redEnvelopReduce = redEnvelop.getReduce();
            }
        }
        return redEnvelopReduce;
    }

    private double calcReduce(double curOrderTotal, Double activityReduce) {
        if (activityReduce!=null) {
            return curOrderTotal-activityReduce;
        }
        return curOrderTotal;
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
