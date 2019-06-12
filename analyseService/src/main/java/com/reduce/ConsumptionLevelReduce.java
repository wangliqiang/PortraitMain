package com.reduce;

import com.entity.ConsumptionLevelInfo;
import com.utils.HbaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * @Description 败家指数
 * @Author wangliqiang
 * @Date 2019/6/3 11:48
 */
public class ConsumptionLevelReduce implements GroupReduceFunction<ConsumptionLevelInfo, ConsumptionLevelInfo> {

    @Override
    public void reduce(Iterable<ConsumptionLevelInfo> iterable, Collector<ConsumptionLevelInfo> collector) throws Exception {
        Iterator<ConsumptionLevelInfo> iterator = iterable.iterator();
        int sum = 0;
        double totalamount = 0d;
        String userid = "-1";
        while (iterator.hasNext()) {
            ConsumptionLevelInfo consumptionLevelInfo = iterator.next();
            userid = consumptionLevelInfo.getUserid();
            String total = consumptionLevelInfo.getAmounttotal();
            double amountDouble = Double.valueOf(total);
            totalamount += amountDouble;
            sum++;
        }
        double avramount = totalamount / sum;// 平均金额 高消费>5000 中等消费 1000-5000 低消费 <1000
        String flag = "low";
        if (avramount < 1000) {
            flag = "low";
        } else if (avramount >= 1000 && avramount < 5000) {
            flag = "middle";
        } else if (avramount >= 5000) {
            flag = "high";
        }

        String tablename = "userflaginfo"; // 表名
        String rowkey = userid;
        String familyname = "consumptioninfo";
        String column = "consumptionlevel";
        String data = flag;

        String resultData = HbaseUtils.getData(tablename, rowkey, familyname, column);
        if (StringUtils.isBlank(resultData)) {
            ConsumptionLevelInfo consumptionLevelInfo = new ConsumptionLevelInfo();
            consumptionLevelInfo.setConsumptiontype(flag);
            consumptionLevelInfo.setCount(1l);
            consumptionLevelInfo.setGroupfield("==consumptionLevelFinal=="+flag);
            collector.collect(consumptionLevelInfo);
        } else if (!resultData.equals(flag)) {
            ConsumptionLevelInfo consumptionLevelInfo = new ConsumptionLevelInfo();
            consumptionLevelInfo.setConsumptiontype(resultData);
            consumptionLevelInfo.setGroupfield("==consumptionLevelFinal=="+resultData);
            consumptionLevelInfo.setCount(-1l);

            ConsumptionLevelInfo consumptionLevelInfo2 = new ConsumptionLevelInfo();
            consumptionLevelInfo2.setConsumptiontype(flag);
            consumptionLevelInfo2.setCount(1l);
            consumptionLevelInfo.setGroupfield("==consumptionLevelFinal=="+flag);
            collector.collect(consumptionLevelInfo);
            collector.collect(consumptionLevelInfo2);
        }
        HbaseUtils.putdata(tablename, rowkey, familyname, column, data);

    }
}
