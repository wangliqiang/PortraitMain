package com.sink;

import com.entity.BrandLike;
import com.utils.MongoUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.bson.Document;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/5 10:23
 */
public class BrandLikeSink implements SinkFunction<BrandLike> {

    @Override
    public void invoke(BrandLike value, Context context) throws Exception {
        String brand = value.getBrand();
        Long count = value.getCount();

        Document doc = MongoUtils.findOneBy("brandlikestatics","userPortrait",brand);
        if(doc == null){
            doc = new Document();
            doc.put("info",brand);
            doc.put("count",count);
        }else{
            Long countPre = doc.getLong("count");
            Long total = countPre + count;
            doc.put("count",total);
        }
        MongoUtils.saveOrUpdateMongo("brandlikestatics","userPortrait",doc);

    }
}
