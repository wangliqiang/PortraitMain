package com.entity;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/6 10:03
 */
public class SexPreInfo {
    /**
     * 用户id 订餐次数 订单频次 浏览男装
     * 浏览小孩 浏览老人 浏览女士
     * 订单平均金额 浏览商品频次 标签
     */
    private int userid;// 用户id
    private long ordernum; // 订餐次数
    private long orderfre; // 订单频次
    private int manclothes; // 浏览男装 次数
    private int childclothes; // 浏览小孩
    private int oldclothes; // 浏览老人
    private int womenclothes; // 浏览女士
    private double averageamount; // 订单平均金额
    private int producttimes; // 浏览商品频次
    private int label; // 标签 男，女

    private String groupfield;

    public String getGroupfield() {
        return groupfield;
    }

    public void setGroupfield(String groupfield) {
        this.groupfield = groupfield;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public long getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(long ordernum) {
        this.ordernum = ordernum;
    }

    public long getOrderfre() {
        return orderfre;
    }

    public void setOrderfre(long orderfre) {
        this.orderfre = orderfre;
    }

    public int getManclothes() {
        return manclothes;
    }

    public void setManclothes(int manclothes) {
        this.manclothes = manclothes;
    }

    public int getChildclothes() {
        return childclothes;
    }

    public void setChildclothes(int childclothes) {
        this.childclothes = childclothes;
    }

    public int getOldclothes() {
        return oldclothes;
    }

    public void setOldclothes(int oldclothes) {
        this.oldclothes = oldclothes;
    }

    public int getWomenclothes() {
        return womenclothes;
    }

    public void setWomenclothes(int womenclothes) {
        this.womenclothes = womenclothes;
    }

    public double getAverageamount() {
        return averageamount;
    }

    public void setAverageamount(double averageamount) {
        this.averageamount = averageamount;
    }

    public int getProducttimes() {
        return producttimes;
    }

    public void setProducttimes(int producttimes) {
        this.producttimes = producttimes;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }
}
