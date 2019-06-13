package com.map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.entity.BrandLike;
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
public class BrandLikeMap implements FlatMapFunction<KafkaEvent, BrandLike> {
    @Override
    public void flatMap(KafkaEvent kafkaEvent, Collector<BrandLike> collector) throws Exception {
        String data = kafkaEvent.getWord();
        ScanProductLog scanProductLog = JSON.parseObject(data, ScanProductLog.class);
        int userid = scanProductLog.getUserid();
        String brand = scanProductLog.getBrand();

        String tablename = "userflaginfo"; // 表名
        String rowkey = userid + "";
        String familyname = "userbehavior";
        String column = "brandlist";
        String mapData = HbaseUtils.getData(tablename, rowkey, familyname, column);

        Map<String, Integer> map = new HashMap<>();
        if (StringUtils.isNotBlank(mapData)) {
            map = JSONObject.parseObject(mapData, Map.class);
        }
        // 获取之前的品牌偏好
        String maxPreBrand = MapUtils.getMaxByMap(map);
        int prebrand = map.get(brand) == null ? 0 : map.get(brand);
        map.put(brand, prebrand + 1);
        HbaseUtils.putdata(tablename, rowkey, familyname, column, JSONObject.toJSONString(map));

        String maxBrand = MapUtils.getMaxByMap(map);

        if (StringUtils.isNotBlank(maxBrand) && !maxPreBrand.equals(maxBrand)) {
            BrandLike preBrandLike = new BrandLike();
            preBrandLike.setBrand(maxPreBrand);
            preBrandLike.setCount(-1l);
            preBrandLike.setGroupfield("==brandlike=="+maxPreBrand);
            collector.collect(preBrandLike);
        }
        BrandLike brandLike = new BrandLike();
        brandLike.setBrand(maxBrand);
        brandLike.setCount(1l);
        brandLike.setGroupfield("==brandlike=="+maxBrand);
        collector.collect(brandLike);

        column = "brandlike";
        HbaseUtils.putdata(tablename, rowkey, familyname, column, maxBrand);
    }
}
