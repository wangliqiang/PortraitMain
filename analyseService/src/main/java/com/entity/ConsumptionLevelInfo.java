package com.entity;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 10:51
 */
public class ConsumptionLevelInfo {
    private String consumptiontype; // 消费水平  高，中，低
    private Long count; // 数量
    private String groupfield; // 分组字段
    private String userid;
    private String amounttotal;// 金额

    public String getConsumptiontype() {
        return consumptiontype;
    }

    public void setConsumptiontype(String consumptiontype) {
        this.consumptiontype = consumptiontype;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getGroupfield() {
        return groupfield;
    }

    public void setGroupfield(String groupfield) {
        this.groupfield = groupfield;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAmounttotal() {
        return amounttotal;
    }

    public void setAmounttotal(String amounttotal) {
        this.amounttotal = amounttotal;
    }
}
