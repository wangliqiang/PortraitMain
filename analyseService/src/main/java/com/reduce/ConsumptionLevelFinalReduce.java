package com.reduce;

import com.entity.ConsumptionLevelInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 11:48
 */
public class ConsumptionLevelFinalReduce implements ReduceFunction<ConsumptionLevelInfo> {

    @Override
    public ConsumptionLevelInfo reduce(ConsumptionLevelInfo consumptionLevelInfo1, ConsumptionLevelInfo consumptionLevelInfo2) throws Exception {
        String consumptiontype = consumptionLevelInfo1.getConsumptiontype();
        Long count1 = consumptionLevelInfo1.getCount();

        Long count2 = consumptionLevelInfo2.getCount();

        ConsumptionLevelInfo consumptionLevelInfo = new ConsumptionLevelInfo();
        consumptionLevelInfo.setConsumptiontype(consumptiontype);
        consumptionLevelInfo.setCount(count1 + count2);
        return consumptionLevelInfo;
    }
}
