package com.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/5 10:01
 */
public class MapUtils {
    public static String getMaxByMap(Map<String, Long> dataMap) {
        if(dataMap.isEmpty()){
            return null;
        }
        TreeMap<Long, String> map = new TreeMap<Long, String>(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o2.compareTo(o1);
            }
        });
        Set<Map.Entry<String, Long>> set = dataMap.entrySet();
        for (Map.Entry<String, Long> entry : set) {
            String key = entry.getKey();
            Long value = entry.getValue();

            map.put(value, key);
        }
        return map.firstEntry().getValue();
    }
}
