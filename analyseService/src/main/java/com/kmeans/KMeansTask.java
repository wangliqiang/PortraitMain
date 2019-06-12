package com.kmeans;


import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

import java.util.*;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/10 11:00
 */
public class KMeansTask {
    public static void main(String[] args) throws Exception {
        ParameterTool params = ParameterTool.fromArgs(args);

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataSet<String> text = env.readTextFile(params.get("input"));
        DataSet<KMeansInfo> mapResult = text.map(new KMeansMap());

        DataSet<ArrayList<Point>> reduce = mapResult.groupBy("groupbyfield").reduceGroup(new KMeansReduce());

        List<ArrayList<Point>> resultList = reduce.collect();
        ArrayList<float[]> dataSet = new ArrayList<>();
        for (ArrayList<Point> arrayList : resultList){
            for (Point point:arrayList){
                dataSet.add(point.getLocalArray());
            }
        }

        KMeansRun kMeansRun = new KMeansRun(6, dataSet);
        Set<Cluster> clusterSet = kMeansRun.run();
        ArrayList<Point> finalArrayList = new ArrayList<>();
        int count = 100;
        for (Cluster cluster : clusterSet) {
            Point point = cluster.getCenter();
            point.setId(count++);
            finalArrayList.add(point);
        }

        DataSet<Point> finalMap = text.map(new KMeansFinalMap(finalArrayList));
        finalMap.writeAsText(params.get("out"));// 保存到hdfs

        env.execute("KMeansTask execute!");
    }
}
