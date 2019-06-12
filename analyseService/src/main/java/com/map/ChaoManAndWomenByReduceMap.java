package com.map;

import com.alibaba.fastjson.JSONObject;
import com.entity.ChaoManAndWomenInfo;
import com.utils.HbaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.*;


/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/11 9:39
 */
public class ChaoManAndWomenByReduceMap implements FlatMapFunction<ChaoManAndWomenInfo, ChaoManAndWomenInfo> {
    @Override
    public void flatMap(ChaoManAndWomenInfo chaoManAndWomenInfo, Collector<ChaoManAndWomenInfo> collector) throws Exception {

        Map<String, Long> resultMap = new HashMap<>();
        String rowkey = "-1";
        String chaotype = chaoManAndWomenInfo.getChaotype();
        if (rowkey.equals("-1")) {
            rowkey = chaoManAndWomenInfo.getUserid();
        }
        long count = chaoManAndWomenInfo.getCount();
        long pre = resultMap.get(chaotype) == null ? 0l : resultMap.get(chaotype);
        resultMap.put(chaotype, pre + count);
        String tablename = "userflaginfo";
        String familyname = "userbehavior";
        String column = "chaomanandwomen";
        String data = HbaseUtils.getData(tablename, rowkey, familyname, column);

        if (StringUtils.isNotBlank(data)) {
            Map<String, Long> map = JSONObject.parseObject(data, Map.class);
            Set<String> set = resultMap.keySet();
            for (String key : set) {
                Long pre1 = map.get(key) == null ? 0l : map.get(key);
                resultMap.put(key, pre1 + resultMap.get(key));
            }
        }
        if (!resultMap.isEmpty()) {
            String chaoManAndWomen = JSONObject.toJSONString(resultMap);
            HbaseUtils.putdata(tablename, rowkey, familyname, column, chaoManAndWomen);
            long chaoMan = resultMap.get("1") == null ? 0l : resultMap.get("1");
            long chaoWomen = resultMap.get("2") == null ? 0l : resultMap.get("2");
            String flag = "women";
            long finalCount = chaoWomen;
            if (chaoMan > chaoWomen) {
                flag = "man";
                finalCount = chaoMan;
            }
            if (finalCount > 2000) {
                column = "chaotype";

                ChaoManAndWomenInfo chaoManAndWomenInfoTemp = new ChaoManAndWomenInfo();
                chaoManAndWomenInfoTemp.setChaotype(flag);
                chaoManAndWomenInfoTemp.setCount(1l);
                chaoManAndWomenInfoTemp.setGroupfield(flag + "==chaoManAndWomenInfoReduce");
                String type = HbaseUtils.getData(tablename, rowkey, familyname, column);
                if (StringUtils.isNotBlank(type) && !type.equals(flag)) {
                    ChaoManAndWomenInfo chaoManAndWomenInfoPre = new ChaoManAndWomenInfo();
                    chaoManAndWomenInfoPre.setChaotype(flag);
                    chaoManAndWomenInfoPre.setCount(-1l);
                    chaoManAndWomenInfoPre.setGroupfield(type + "==chaoManAndWomenInfoReduce");
                    collector.collect(chaoManAndWomenInfoPre);
                }

                HbaseUtils.putdata(tablename, rowkey, familyname, column, flag);
                collector.collect(chaoManAndWomenInfoTemp);
            }
        }
    }
}
