package com.reduce;

import com.entity.BaiJiaInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 败家指数
 * @Author wangliqiang
 * @Date 2019/6/3 11:48
 */
public class BaiJiaReduce implements ReduceFunction<BaiJiaInfo> {

    @Override
    public BaiJiaInfo reduce(BaiJiaInfo baiJiaInfo, BaiJiaInfo t1) throws Exception {
        String userid = baiJiaInfo.getUserid();
        List<BaiJiaInfo> baiJiaList1 = baiJiaInfo.getList();
        List<BaiJiaInfo> baiJiaList2 = t1.getList();

        List<BaiJiaInfo> finalList = new ArrayList<BaiJiaInfo>();
        finalList.addAll(baiJiaList1);
        finalList.addAll(baiJiaList2);

        BaiJiaInfo finalBaiJiaInfo = new BaiJiaInfo();
        finalBaiJiaInfo.setUserid(userid);
        finalBaiJiaInfo.setList(finalList);

        return finalBaiJiaInfo;
    }
}
