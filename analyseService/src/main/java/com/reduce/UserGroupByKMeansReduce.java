package com.reduce;

import com.entity.UserGroupInfo;
import com.kmeans.*;
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
public class UserGroupByKMeansReduce implements GroupReduceFunction<UserGroupInfo, ArrayList<Point>> {


    @Override
    public void reduce(Iterable<UserGroupInfo> iterable, Collector<ArrayList<Point>> collector) throws Exception {
        Iterator<UserGroupInfo> iterator = iterable.iterator();
        ArrayList<float[]> dataSet = new ArrayList<>();
        while (iterator.hasNext()) {
            UserGroupInfo userGroupInfo = iterator.next();
            float[] f = new float[]{
                    Float.valueOf(userGroupInfo.getUserid()),
                    Float.valueOf(userGroupInfo.getAverageamount() + ""),
                    Float.valueOf(userGroupInfo.getMaxamount() + ""),
                    Float.valueOf(userGroupInfo.getDays() + ""),
                    Float.valueOf(userGroupInfo.getBuytype1() + ""),
                    Float.valueOf(userGroupInfo.getBuytype2() + ""),
                    Float.valueOf(userGroupInfo.getBuytype3() + ""),
                    Float.valueOf(userGroupInfo.getBuytime1() + ""),
                    Float.valueOf(userGroupInfo.getBuytime2() + ""),
                    Float.valueOf(userGroupInfo.getBuytime3() + ""),
                    Float.valueOf(userGroupInfo.getBuytime4() + "")};

            dataSet.add(f);
        }

        KMeansRunByUserGroup kMeansRunByUserGroup = new KMeansRunByUserGroup(6, dataSet);

        Set<Cluster> clusterSet = kMeansRunByUserGroup.run();

        ArrayList<Point> arrayList = new ArrayList<>();
        for (Cluster cluster : clusterSet) {
            arrayList.add(cluster.getCenter());
        }
        collector.collect(arrayList);
    }
}
