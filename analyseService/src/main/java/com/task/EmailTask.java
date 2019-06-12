package com.task;

import com.entity.EmailInfo;
import com.map.EmailMap;
import com.reduce.EmailReduce;
import com.utils.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.util.List;

/**
 * @Description 邮箱
 * @Author wangliqiang
 * @Date 2019/6/3 10:48
 */
public class EmailTask {
    public static void main(String[] args) throws Exception {
        ParameterTool params = ParameterTool.fromArgs(args);

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataSet<String> text = env.readTextFile(params.get("input"));
        DataSet<EmailInfo> mapResult = text.map(new EmailMap());

        DataSet<EmailInfo> reduce = mapResult.groupBy("groupfield").reduce(new EmailReduce());

        List<EmailInfo> resultList = reduce.collect();

        for (EmailInfo emailInfo : resultList) {
            String emailtype = emailInfo.getEmailtype();
            Long count = emailInfo.getCount();
            Document doc = MongoUtils.findOneBy("emailstatics", "userPortrait", emailtype);
            if (doc == null) {
                doc = new Document();
                doc.put("info", emailtype);
                doc.put("count", count);
            } else {
                Long countPre = doc.getLong("count");
                Long total = countPre + count;
                doc.put("count", total);
            }
            MongoUtils.saveOrUpdateMongo("emailstatics", "userPortrait", doc);
        }
        env.execute("EmailTask execute!");

    }
}
