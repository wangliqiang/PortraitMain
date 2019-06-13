package com.map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.entity.UseTypeInfo;
import com.kafka.KafkaEvent;
import com.log.ScanProductLog;
import com.utils.HbaseUtils;
import com.utils.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/5 9:41
 */
public class UseTypeMap implements FlatMapFunction<KafkaEvent, UseTypeInfo> {
    @Override
    public void flatMap(KafkaEvent kafkaEvent, Collector<UseTypeInfo> collector) throws Exception {
        String data = kafkaEvent.getWord();
        ScanProductLog scanProductLog = JSON.parseObject(data, ScanProductLog.class);
        int userid = scanProductLog.getUserid();
        int usetype = scanProductLog.getUsetype(); // 终端类型：0：PC端，1：移动端，2：小程序端

        String usetypename = usetype == 0 ? "pc端" : usetype == 1 ? "移动端" : "小程序端";

        String tablename = "userflaginfo"; // 表名
        String rowkey = userid + "";
        String familyname = "userbehavior";
        String column = "usetypelist"; // 运营商
        String mapData = HbaseUtils.getData(tablename, rowkey, familyname, column);

        Map<String, Integer> map = new HashMap<>();
        if (StringUtils.isNotBlank(mapData)) {
            map = JSONObject.parseObject(mapData, Map.class);
        }
        // 获取之前的终端偏好
        String maxPreUsetype = MapUtils.getMaxByMap(map);
        int preUseType = map.get(usetypename) == null ? 0 : map.get(usetypename);
        map.put(usetypename, preUseType + 1);
        HbaseUtils.putdata(tablename, rowkey, familyname, column, JSONObject.toJSONString(map));

        String maxUsetype = MapUtils.getMaxByMap(map);

        if (StringUtils.isNotBlank(maxUsetype) && !maxPreUsetype.equals(maxUsetype)) {
            UseTypeInfo useTypeInfo = new UseTypeInfo();
            useTypeInfo.setUsertype(maxPreUsetype);
            useTypeInfo.setCount(-1l);
            useTypeInfo.setGroupfield("==usetype==" + maxPreUsetype);
            collector.collect(useTypeInfo);
        }
        UseTypeInfo useTypeInfo = new UseTypeInfo();
        useTypeInfo.setUsertype(maxUsetype);
        useTypeInfo.setCount(1l);
        useTypeInfo.setGroupfield("==usetypeinfo==" + maxUsetype);
        collector.collect(useTypeInfo);

        column = "usetype";
        HbaseUtils.putdata(tablename, rowkey, familyname, column, maxUsetype);
    }
}
