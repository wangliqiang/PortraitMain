package com.reduce;

import com.entity.YearBase;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 11:48
 */
public class YearBaseReduce implements ReduceFunction<YearBase> {

    @Override
    public YearBase reduce(YearBase yearBase, YearBase t1) throws Exception {
        String yearType = yearBase.getYeartype();
        Long count1 = yearBase.getCount();

        Long count2 = t1.getCount();

        YearBase finalYearBase = new YearBase();
        finalYearBase.setYeartype(yearType);
        finalYearBase.setCount(count1 + count2);
        return finalYearBase;
    }
}
