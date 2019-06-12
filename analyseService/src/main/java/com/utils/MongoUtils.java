package com.utils;

import com.alibaba.fastjson.JSON;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * @Description MongoDB 工具类
 * @Author wangliqiang
 * @Date 2019/6/3 13:33
 */
public class MongoUtils {

    private static MongoClient mongoClient = new MongoClient("flink01", 27017);

    public static Document findOneBy(String tableName, String database, String info) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
        MongoCollection mongoCollection = mongoDatabase.getCollection(tableName);
        Document doc = new Document();
        doc.put("info", info);

        FindIterable<Document> findIterable = mongoCollection.find(doc);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        if (mongoCursor.hasNext()) {
            return mongoCursor.next();
        } else {
            return null;
        }
    }

    public static void saveOrUpdateMongo(String tableName, String database, Document doc) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(tableName);
        if (!doc.containsKey("_id")) {
            ObjectId objectId = new ObjectId();
            doc.put("_id", objectId);
            mongoCollection.insertOne(doc);
            System.out.println("saveOrUpdateMongo--insert--" + JSON.toJSONString(doc));
            return;
        }
        Document matchDocument = new Document();
        String objectId = doc.get("_id").toString();
        matchDocument.put("_id", new ObjectId(objectId));
        FindIterable<Document> findIterable = mongoCollection.find(matchDocument);
        if (findIterable.iterator().hasNext()) {
            mongoCollection.updateOne(matchDocument, new Document("$set", doc));
            System.out.println("saveOrUpdateMongo--update--" + JSON.toJSONString(doc));
        } else {
            mongoCollection.insertOne(doc);
            System.out.println("saveOrUpdateMongo--insert--" + JSON.toJSONString(doc));
        }
    }
}
