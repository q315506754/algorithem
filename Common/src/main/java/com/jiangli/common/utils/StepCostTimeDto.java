package com.jiangli.common.utils;


/**
 * 统计dubbo请求耗时信息dto
 *
 * @author Jiangli
 * @date 2016/10/27 10:54
 *
 */
public class StepCostTimeDto  {
   private String name;//该步骤名字
    private Long cost;//该步骤耗时
   private String msg="ok";//消息，出错时为异常消息

    public StepCostTimeDto(String name, Long cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public StepCostTimeDto(String name, Long cost, String msg) {
        this.name = name;
        this.cost = cost;
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }
}
