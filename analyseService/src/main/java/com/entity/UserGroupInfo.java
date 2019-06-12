package com.entity;

import java.util.List;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/10 14:08
 */
public class UserGroupInfo {
    private String userid; // 用户id
    private String createtime; // 创建时间
    private String amount;
    private String paytype;
    private String paytime;
    private String paystatus; // 0、未支付；1、已支付；2、已退款
    private String couponamount;
    private String totalamount;
    private String refundamount;
    private String producttypeid;// 消费类目
    private Long count;
    private String groupfield; // 分组
    private List<UserGroupInfo> list;// 用户所有的信息

    private double averageamount;//平均消费金额
    private double maxamount;//最大消费金额
    private int days;//消费频次
    private Long buytype1;//消费类目1数量,电子（电脑，手机，电视） 生活家居（衣服，生活用品，床上用品）  生鲜（油，米，肉类）
    private Long buytype2;//消费类目2数量,电子（电脑，手机，电视） 生活家居（衣服，生活用品，床上用品）  生鲜（油，米，肉类）
    private Long buytype3;//消费类目3数量,电子（电脑，手机，电视） 生活家居（衣服，生活用品，床上用品）  生鲜（油，米，肉类）
    private Long buytime1;//消费时间点1数量, 上午、下午、晚上、凌晨
    private Long buytime2;//消费时间点2数量, 上午、下午、晚上、凌晨
    private Long buytime3;//消费时间点3数量, 上午、下午、晚上、凌晨
    private Long buytime4;//消费时间点4数量, 上午、下午、晚上、凌晨


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public String getCouponamount() {
        return couponamount;
    }

    public void setCouponamount(String couponamount) {
        this.couponamount = couponamount;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getRefundamount() {
        return refundamount;
    }

    public void setRefundamount(String refundamount) {
        this.refundamount = refundamount;
    }

    public String getProducttypeid() {
        return producttypeid;
    }

    public void setProducttypeid(String producttypeid) {
        this.producttypeid = producttypeid;
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

    public List<UserGroupInfo> getList() {
        return list;
    }

    public void setList(List<UserGroupInfo> list) {
        this.list = list;
    }

    public double getAverageamount() {
        return averageamount;
    }

    public void setAverageamount(double averageamount) {
        this.averageamount = averageamount;
    }

    public double getMaxamount() {
        return maxamount;
    }

    public void setMaxamount(double maxamount) {
        this.maxamount = maxamount;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Long getBuytype1() {
        return buytype1;
    }

    public void setBuytype1(Long buytype1) {
        this.buytype1 = buytype1;
    }

    public Long getBuytype2() {
        return buytype2;
    }

    public void setBuytype2(Long buytype2) {
        this.buytype2 = buytype2;
    }

    public Long getBuytype3() {
        return buytype3;
    }

    public void setBuytype3(Long buytype3) {
        this.buytype3 = buytype3;
    }

    public Long getBuytime1() {
        return buytime1;
    }

    public void setBuytime1(Long buytime1) {
        this.buytime1 = buytime1;
    }

    public Long getBuytime2() {
        return buytime2;
    }

    public void setBuytime2(Long buytime2) {
        this.buytime2 = buytime2;
    }

    public Long getBuytime3() {
        return buytime3;
    }

    public void setBuytime3(Long buytime3) {
        this.buytime3 = buytime3;
    }

    public Long getBuytime4() {
        return buytime4;
    }

    public void setBuytime4(Long buytime4) {
        this.buytime4 = buytime4;
    }
}
