package com.reduce;

import com.entity.ChaoManAndWomenInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

import java.util.List;

/**
 * @Description 败家指数
 * @Author wangliqiang
 * @Date 2019/6/3 11:48
 */
public class ChaoManAndWomenReduce implements ReduceFunction<ChaoManAndWomenInfo> {

    @Override
    public ChaoManAndWomenInfo reduce(ChaoManAndWomenInfo chaoManAndWomenInfo1, ChaoManAndWomenInfo chaoManAndWomenInfo2) throws Exception {
        String userid = chaoManAndWomenInfo1.getUserid();
        List<ChaoManAndWomenInfo> list1 = chaoManAndWomenInfo1.getList();
        List<ChaoManAndWomenInfo> list2 = chaoManAndWomenInfo2.getList();
        list1.addAll(list2);

        ChaoManAndWomenInfo finalChaoManAndWomenInfo = new ChaoManAndWomenInfo();
        finalChaoManAndWomenInfo.setUserid(userid);
        finalChaoManAndWomenInfo.setList(list1);
        return finalChaoManAndWomenInfo;
    }
}
