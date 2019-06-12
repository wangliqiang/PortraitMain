package com.sink;

import com.entity.UseTypeInfo;
import com.utils.MongoUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.bson.Document;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/5 10:23
 */
public class UseTypeSink implements SinkFunction<UseTypeInfo> {

    @Override
    public void invoke(UseTypeInfo value, Context context) throws Exception {
        String usertype = value.getUsertype();
        Long count = value.getCount();

        Document doc = MongoUtils.findOneBy("usetypestatics","userPortrait",usertype);
        if(doc == null){
            doc = new Document();
            doc.put("info",usertype);
            doc.put("count",count);
        }else{
            Long countPre = doc.getLong("count");
            Long total = countPre + count;
            doc.put("count",total);
        }
        MongoUtils.saveOrUpdateMongo("usetypestatics","userPortrait",doc);

    }
}
