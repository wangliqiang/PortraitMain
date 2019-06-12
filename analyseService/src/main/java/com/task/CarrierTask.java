package com.task;

import com.entity.CarrierInfo;
import com.map.CarrierMap;
import com.reduce.CarrierReduce;
import com.utils.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.util.List;

/**
 * @Description 运营商
 * @Author wangliqiang
 * @Date 2019/6/3 10:48
 */
public class CarrierTask {
    public static void main(String[] args) throws Exception {
        ParameterTool params = ParameterTool.fromArgs(args);

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataSet<String> text = env.readTextFile(params.get("input"));
        DataSet<CarrierInfo> mapResult = text.map(new CarrierMap());

        DataSet<CarrierInfo> reduce = mapResult.groupBy("groupfield").reduce(new CarrierReduce());

        List<CarrierInfo> resultList = reduce.collect();

        for (CarrierInfo carrierInfo : resultList){
            String carrier = carrierInfo.getCarrier();
            Long count = carrierInfo.getCount();
            Document doc = MongoUtils.findOneBy("carrierstatics","userPortrait",carrier);
            if(doc == null){
                doc = new Document();
                doc.put("info",carrier);
                doc.put("count",count);
            }else{
                Long countPre = doc.getLong("count");
                Long total = countPre + count;
                doc.put("count",total);
            }
            MongoUtils.saveOrUpdateMongo("carrierstatics","userPortrait",doc);
        }
        env.execute("CarrierTask execute!");

    }
}
