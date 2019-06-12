package com.search.service;

import com.alibaba.fastjson.JSONObject;
import com.entity.AnalyseResult;
import com.mongodb.client.*;
import com.search.base.BaseMongo;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/11 14:23
 */
@Service
public class MongoDataServiceImpl extends BaseMongo {


    public List<AnalyseResult> listMongoDataBy(String tablename) {

        List<AnalyseResult> result = new ArrayList<>();

        MongoDatabase db = mongoClient.getDatabase(MONGODB_DATABASE);
        MongoCollection<Document> collection = db.getCollection(tablename);

        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> cursor = findIterable.iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            String jsonString = JSONObject.toJSONString(document);
            AnalyseResult analyseResult = JSONObject.parseObject(jsonString, AnalyseResult.class);
            result.add(analyseResult);
        }
        return result;
    }
}
