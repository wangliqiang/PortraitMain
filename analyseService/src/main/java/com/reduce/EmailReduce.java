package com.reduce;

import com.entity.EmailInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 11:48
 */
public class EmailReduce implements ReduceFunction<EmailInfo> {

    @Override
    public EmailInfo reduce(EmailInfo emailInfo, EmailInfo t1) throws Exception {
        String emailtype = emailInfo.getEmailtype();
        Long count1 = emailInfo.getCount();

        Long count2 = t1.getCount();

        EmailInfo finalEmailInfo = new EmailInfo();
        finalEmailInfo.setEmailtype(emailtype);
        finalEmailInfo.setCount(count1 + count2);
        return finalEmailInfo;
    }
}
