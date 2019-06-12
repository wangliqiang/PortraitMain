package com.logic;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

import java.util.*;

/**
 * @Description 逻辑回归
 * @Author wangliqiang
 * @Date 2019/6/3 10:48
 */
public class LogicTask {
    public static void main(String[] args) throws Exception {
        ParameterTool params = ParameterTool.fromArgs(args);

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataSet<String> text = env.readTextFile(params.get("input"));
        DataSet<LogicInfo> mapResult = text.map(new LogicMap());

        DataSet<ArrayList<Double>> reduce = mapResult.groupBy("groupbyfield").reduceGroup(new LogicReduce());

        List<ArrayList<Double>> resultList = reduce.collect();
        int groupSize = resultList.size();
        Map<Integer,Double> sumMap = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        for (ArrayList<Double> arrayList : resultList){
            for (int i = 0;i<arrayList.size();i++) {
                double pre = sumMap.get(i) == null?0d:sumMap.get(i);
                sumMap.put(i,pre+ arrayList.get(i));
            }
        }

        ArrayList<Double> finalWeight = new ArrayList<Double>();
        Set<Map.Entry<Integer,Double>> set = sumMap.entrySet();
        for (Map.Entry<Integer,Double> mapEntry:set){
            Integer key = mapEntry.getKey();
            Double sumValue = mapEntry.getValue();
            double finalValue = sumValue/groupSize;
            finalWeight.add(finalValue);
        }
        env.execute("LogicTask execute!");

    }
}
