package com.map;

import com.alibaba.fastjson.JSON;
import com.entity.ChaoManAndWomenInfo;
import com.kafka.KafkaEvent;
import com.log.ScanProductLog;
import com.utils.ReadProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/11 9:39
 */
public class ChaoManAndWomenMap implements FlatMapFunction<KafkaEvent, ChaoManAndWomenInfo> {
    @Override
    public void flatMap(KafkaEvent kafkaEvent, Collector<ChaoManAndWomenInfo> collector) throws Exception {
        String data = kafkaEvent.getWord();
        ScanProductLog scanProductLog = JSON.parseObject(data, ScanProductLog.class);
        int userid = scanProductLog.getUserid();
        int productId = scanProductLog.getProductid();

        String chaotype = ReadProperties.getKey(productId + "", "productChaoLiudic.properties");

        ChaoManAndWomenInfo chaoManAndWomenInfo = new ChaoManAndWomenInfo();
        chaoManAndWomenInfo.setUserid(userid + "");
        if (StringUtils.isNoneBlank(chaotype)) {
            chaoManAndWomenInfo.setChaotype(chaotype);
            chaoManAndWomenInfo.setCount(1l);
            chaoManAndWomenInfo.setGroupfield("chaoManAndWomen==" + userid);
            List<ChaoManAndWomenInfo> list = new ArrayList<>();
            list.add(chaoManAndWomenInfo);
            chaoManAndWomenInfo.setList(list);
            collector.collect(chaoManAndWomenInfo);
        }
    }
}
