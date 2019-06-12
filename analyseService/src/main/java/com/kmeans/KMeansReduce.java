package com.kmeans;

import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 11:48
 */
public class KMeansReduce implements GroupReduceFunction<KMeansInfo, ArrayList<Point>> {


    @Override
    public void reduce(Iterable<KMeansInfo> iterable, Collector<ArrayList<Point>> collector) throws Exception {
        Iterator<KMeansInfo> iterator = iterable.iterator();
        ArrayList<float[]> dataSet = new ArrayList<>();
        while (iterator.hasNext()) {
            KMeansInfo kMeansInfo = iterator.next();
            String variable1 = kMeansInfo.getVariable1();
            String variable2 = kMeansInfo.getVariable2();
            String variable3 = kMeansInfo.getVariable3();
            float[] f = new float[]{Float.valueOf(variable1), Float.valueOf(variable2), Float.valueOf(variable3)};

            dataSet.add(f);
        }
        KMeansRun kMeansRun = new KMeansRun(6, dataSet);
        Set<Cluster> clusterSet = kMeansRun.run();

        ArrayList<Point> arrayList = new ArrayList<>();
        for (Cluster cluster : clusterSet) {
            arrayList.add(cluster.getCenter());
        }
        collector.collect(arrayList);
    }
}
