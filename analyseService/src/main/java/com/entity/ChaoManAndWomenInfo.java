package com.entity;

import java.util.List;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/11 9:41
 */
public class ChaoManAndWomenInfo {
    private String chaotype;// 潮男：1；潮女：2；
    private String userid;// 用户id
    private long count;
    private String groupfield;
    private List<ChaoManAndWomenInfo> list;

    public List<ChaoManAndWomenInfo> getList() {
        return list;
    }

    public void setList(List<ChaoManAndWomenInfo> list) {
        this.list = list;
    }

    public String getChaotype() {
        return chaotype;
    }

    public void setChaotype(String chaotype) {
        this.chaotype = chaotype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getGroupfield() {
        return groupfield;
    }

    public void setGroupfield(String groupfield) {
        this.groupfield = groupfield;
    }
}
