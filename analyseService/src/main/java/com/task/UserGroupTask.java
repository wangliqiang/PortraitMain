package com.task;

import com.entity.UserGroupInfo;
import com.kmeans.*;
import com.map.KMeansFinalUserGroupMap;
import com.map.UserGroupMap;
import com.map.UserGroupMapByReduce;
import com.reduce.UserGroupByKMeansReduce;
import com.reduce.UserGroupReduce;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description 运营商
 * @Author wangliqiang
 * @Date 2019/6/3 10:48
 */
public class UserGroupTask {
    public static void main(String[] args) throws Exception {
        ParameterTool params = ParameterTool.fromArgs(args);

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataSet<String> text = env.readTextFile(params.get("input"));
        DataSet<UserGroupInfo> mapResult = text.map(new UserGroupMap());

        DataSet<UserGroupInfo> reduce = mapResult.groupBy("groupfield").reduce(new UserGroupReduce());
        DataSet<UserGroupInfo> mapByReduce = reduce.map(new UserGroupMapByReduce());
        DataSet<ArrayList<Point>> finalResult = mapByReduce.groupBy("groupfield").reduceGroup(new UserGroupByKMeansReduce());

        List<ArrayList<Point>> resultList = finalResult.collect();
        ArrayList<float[]> dataSet = new ArrayList<>();
        for (ArrayList<Point> arrayList : resultList){
            for (Point point:arrayList){
                dataSet.add(point.getLocalArray());
            }
        }

        KMeansRunByUserGroup kMeansRunByUserGroup = new KMeansRunByUserGroup(6, dataSet);
        Set<Cluster> clusterSet = kMeansRunByUserGroup.run();
        ArrayList<Point> finalArrayList = new ArrayList<>();
        int count = 100;
        for (Cluster cluster : clusterSet) {
            Point point = cluster.getCenter();
            point.setId(count++);
            finalArrayList.add(point);
        }

        DataSet<Point> finalMap = mapByReduce.map(new KMeansFinalUserGroupMap(finalArrayList));
        env.execute("UserGroupTask execute!");

    }
}
