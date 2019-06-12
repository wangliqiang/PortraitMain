package com.reduce;

import com.entity.ChaoManAndWomenInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 11:48
 */
public class ChaoManAndWomenFinalReduce implements ReduceFunction<ChaoManAndWomenInfo> {

    @Override
    public ChaoManAndWomenInfo reduce(ChaoManAndWomenInfo chaoManAndWomenInfo1, ChaoManAndWomenInfo chaoManAndWomenInfo2) throws Exception {
        String chaotype = chaoManAndWomenInfo1.getChaotype();
        Long count1 = chaoManAndWomenInfo1.getCount();

        Long count2 = chaoManAndWomenInfo2.getCount();

        ChaoManAndWomenInfo finalChaoManAndWomenInfo = new ChaoManAndWomenInfo();
        finalChaoManAndWomenInfo.setChaotype(chaotype);
        finalChaoManAndWomenInfo.setCount(count1 + count2);
        return finalChaoManAndWomenInfo;
    }
}
