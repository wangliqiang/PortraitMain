package com.sink;

import com.entity.ChaoManAndWomenInfo;
import com.utils.MongoUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.bson.Document;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/11 10:25
 */
public class ChaoManAndWomenSink implements SinkFunction<ChaoManAndWomenInfo> {

    @Override
    public void invoke(ChaoManAndWomenInfo value, Context context) throws Exception {
        String chaotype = value.getChaotype();
        Long count = value.getCount();

        Document doc = MongoUtils.findOneBy("chaoManAndWomenstatics","userPortrait",chaotype);
        if(doc == null){
            doc = new Document();
            doc.put("info",chaotype);
            doc.put("count",count);
        }else{
            Long countPre = doc.getLong("count");
            Long total = countPre + count;
            doc.put("count",total);
        }
        MongoUtils.saveOrUpdateMongo("chaoManAndWomenstatics","userPortrait",doc);
    }
}
