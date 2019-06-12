package com.utils;

/**
 * @Description 邮箱工具类
 * @Author wangliqiang
 * @Date 2019/6/3 14:32
 */
public class EmailUtils {

    /**
     * 网易邮箱 @163.com @126.com
     * 移动邮箱 @139.com
     * 搜狐邮箱 @sohu.com
     * QQ邮箱   @qq.com
     * 电信邮箱 @189.cn
     * TOM邮箱  @tom.com
     * 新浪邮箱 @sina.com
     * 阿里邮箱 @aliyun.com
     *
     * @param email
     * @return
     */
    public static String getEmailTypeBy(String email) {
        String emailType = "其他邮箱用户";
        if (email.contains("@163.com") || email.contains("@126.com")) {
            emailType = "网易邮箱用户";
        } else if (email.contains("@139.com")) {
            emailType = "移动邮箱用户";
        } else if (email.contains("@sohu.com")) {
            emailType = "搜狐邮箱用户";
        } else if (email.contains("@qq.com")) {
            emailType = "QQ邮箱用户";
        } else if (email.contains("@189.com")) {
            emailType = "电信邮箱用户";
        } else if (email.contains("@tom.com")) {
            emailType = "TOM邮箱用户";
        } else if (email.contains("@sina.com")) {
            emailType = "新浪邮箱用户";
        } else if (email.contains("@aliyun.com")) {
            emailType = "阿里邮箱用户";
        }
        return emailType;
    }

}
