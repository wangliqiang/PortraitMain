package com.entity;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 14:50
 */
public class EmailInfo {
    private String emailtype;// 邮箱类型
    private Long count;
    private String groupfield;// 分组

    public String getEmailtype() {
        return emailtype;
    }

    public void setEmailtype(String emailtype) {
        this.emailtype = emailtype;
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
}
