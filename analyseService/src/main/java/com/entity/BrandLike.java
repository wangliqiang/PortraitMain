package com.entity;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/5 9:42
 */
public class BrandLike {
    private String brand;
    private long count;
    private String groupfield;

    public String getGroupfield() {
        return groupfield;
    }

    public void setGroupfield(String groupfield) {
        this.groupfield = groupfield;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
