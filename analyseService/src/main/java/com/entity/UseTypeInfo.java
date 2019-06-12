package com.entity;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/5 9:42
 */
public class UseTypeInfo {
    private String usertype;// 终端类型：0：PC端，1：移动端，2：小程序端
    private long count;
    private String groupfield;

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getGroupfield() {
        return groupfield;
    }

    public void setGroupfield(String groupfield) {
        this.groupfield = groupfield;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
