package com.entity;

import java.util.List;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/11 14:31
 */
public class ViewResultAnalyse {
    private List<String> infolist; // 分组list；x轴的值
    private List<Long> countlist; // 数量list； y轴的值

    public List<String> getInfolist() {
        return infolist;
    }

    public void setInfolist(List<String> infolist) {
        this.infolist = infolist;
    }

    public List<Long> getCountlist() {
        return countlist;
    }

    public void setCountlist(List<Long> countlist) {
        this.countlist = countlist;
    }
}
