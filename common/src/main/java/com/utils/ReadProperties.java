package com.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/4 16:36
 */
public class ReadProperties {
    public final static Config config = ConfigFactory.load("infoinservice.properties");

    public static String getKey(String key) {
        return config.getString(key).trim();
    }
    public static String getKey(String key,String filename) {
        Config config = ConfigFactory.load(filename);
        return config.getString(key).trim();
    }

}
