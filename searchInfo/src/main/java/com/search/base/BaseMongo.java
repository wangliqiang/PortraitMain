package com.search.base;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.utils.ReadProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/11 14:24
 */
public class BaseMongo {
    protected static MongoClient mongoClient;

    public static String MONGODB_DATABASE = "userPortrait";

    static {
        List<ServerAddress> addresses = new ArrayList<>();
        String[] addressList = ReadProperties.getKey("mongoAddr", "search.properties").split(",");
        String[] portList = ReadProperties.getKey("mongoPort", "search.properties").split(",");
        for (int i = 0; i < addressList.length; i++) {
            ServerAddress address = new ServerAddress(addressList[i], Integer.parseInt(portList[i]));
            addresses.add(address);
        }
        mongoClient = new MongoClient(addresses);
    }
}
