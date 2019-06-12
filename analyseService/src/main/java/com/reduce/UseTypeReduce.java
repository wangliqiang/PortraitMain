package com.reduce;

import com.entity.UseTypeInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/5 10:13
 */
public class UseTypeReduce implements ReduceFunction<UseTypeInfo> {
    @Override
    public UseTypeInfo reduce(UseTypeInfo useTypeInfo, UseTypeInfo t1) throws Exception {
        String usertype = useTypeInfo.getUsertype();
        long count1 = useTypeInfo.getCount();

        long count2 = t1.getCount();

        UseTypeInfo finalUseType = new UseTypeInfo();
        finalUseType.setUsertype(usertype);
        finalUseType.setCount(count1+count2);

        return finalUseType;
    }
}
