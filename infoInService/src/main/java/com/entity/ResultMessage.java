package com.entity;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/4 15:12
 */
public class ResultMessage {
    private String status;// 状态
    private String message;// 消息内容

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
