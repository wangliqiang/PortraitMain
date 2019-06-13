package com.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/5 10:01
 */
public class MapUtils {
    public static String getMaxByMap(Map<String, Integer> dataMap) {
        if (dataMap.isEmpty()) {
            return null;
        }
        TreeMap<Integer, String> map = new TreeMap<>(Comparator.reverseOrder());
        Set<Map.Entry<String, Integer>> set = dataMap.entrySet();
        for (Map.Entry<String, Integer> entry : set) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            map.put(value, key);
        }
        return map.firstEntry().getValue();
    }

    public static void main(String[] args) throws IOException {
        String tablename = "userflaginfo"; // 表名
        String rowkey = "0";
        String familyname = "userbehavior";
        String column = "brandlist";
        String mapData = HbaseUtils.getData(tablename, rowkey, familyname, column);

        Map<String, Integer> map = new HashMap<>();
        map = JSONObject.parseObject(mapData, Map.class);
        // 获取之前的品牌偏好
        String maxPreBrand = MapUtils.getMaxByMap(map);
        System.out.println(maxPreBrand);
    }
}
