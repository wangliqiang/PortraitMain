package com.task;

import com.entity.YearBase;
import com.map.YearBaseMap;
import com.reduce.YearBaseReduce;
import com.utils.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.util.List;

/**
 * @Description 年龄
 * @Author wangliqiang
 * @Date 2019/6/3 10:48
 */
public class YearBaseTask {
    public static void main(String[] args) throws Exception {
        ParameterTool params = ParameterTool.fromArgs(args);

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataSet<String> text = env.readTextFile(params.get("input"));
        DataSet<YearBase> mapResult = text.map(new YearBaseMap());

        DataSet<YearBase> reduce = mapResult.groupBy("groupfield").reduce(new YearBaseReduce());

        List<YearBase> resultList = reduce.collect();

        for (YearBase yearBase : resultList){
            String yearType = yearBase.getYeartype();
            Long count = yearBase.getCount();
            Document doc = MongoUtils.findOneBy("yearbasestatics","userPortrait",yearType);
            if(doc == null){
                doc = new Document();
                doc.put("info",yearType);
                doc.put("count",count);
            }else{
                Long countPre = doc.getLong("count");
                Long total = countPre + count;
                doc.put("count",total);
            }
            MongoUtils.saveOrUpdateMongo("yearbasestatics","userPortrait",doc);
        }
        env.execute("YearBaseTask execute!");

    }
}
