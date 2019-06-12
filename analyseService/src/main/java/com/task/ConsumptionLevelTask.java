package com.task;

import com.entity.ConsumptionLevelInfo;
import com.map.ConsumptionLevelMap;
import com.reduce.ConsumptionLevelFinalReduce;
import com.reduce.ConsumptionLevelReduce;
import com.utils.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.util.*;

/**
 * @Description 品牌偏好
 * @Author wangliqiang
 * @Date 2019/6/5 8:57
 */
public class ConsumptionLevelTask {
    public static void main(String[] args) throws Exception {
        ParameterTool params = ParameterTool.fromArgs(args);

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataSet<String> text = env.readTextFile(params.get("input"));
        DataSet<ConsumptionLevelInfo> mapResult = text.map(new ConsumptionLevelMap());

        DataSet<ConsumptionLevelInfo> reduce = mapResult.groupBy("groupfield").reduceGroup(new ConsumptionLevelReduce());

        DataSet<ConsumptionLevelInfo> reduceFinal = reduce.groupBy("groupfield").reduce(new ConsumptionLevelFinalReduce());

        List<ConsumptionLevelInfo> resultList = reduceFinal.collect();

        for (ConsumptionLevelInfo consumptionLevelInfo : resultList) {
            String userid = consumptionLevelInfo.getUserid();
            String consumptiontype = consumptionLevelInfo.getConsumptiontype();
            Long count = consumptionLevelInfo.getCount();

            Document doc = MongoUtils.findOneBy("consumptionlevelstatics", "userPortrait", consumptiontype);
            if (doc == null) {
                doc = new Document();
                doc.put("info", consumptiontype);
                doc.put("count", count);
            } else {
                Long countPre = doc.getLong("count");
                Long total = countPre + count;
                doc.put("count", total);
            }
            MongoUtils.saveOrUpdateMongo("consumptionlevelstatics", "userPortrait", doc);
        }

        env.execute("ConsumptionLevelTask execute!");

    }
}
