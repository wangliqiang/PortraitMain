package com.kmeans;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 10:50
 */
public class KMeansFinalMap implements MapFunction<String, Point> {

    private List<Point> centers = new ArrayList<>();
    private DistanceCompute disC = new DistanceCompute();

    public KMeansFinalMap(List<Point> centers) {
        this.centers = centers;
    }

    @Override
    public Point map(String s) throws Exception {
        if (StringUtils.isBlank(s)) {
            return null;
        }

        String[] temps = s.split(",");
        Random random = new Random();
        String variable1 = temps[0];
        String variable2 = temps[1];
        String variable3 = temps[2];
        Point self = new Point(1,new float[]{Float.valueOf(variable1),Float.valueOf(variable2),Float.valueOf(variable3)});
        float min_dis = Integer.MAX_VALUE;
        for (Point point: centers){
            float tmp_dis = (float) Math.min(disC.getEuclideanDis(self,point),min_dis);
            if (tmp_dis != min_dis) {
                min_dis = tmp_dis;
                self.setClusterId(point.getId());
                self.setDist(min_dis);
                self.setClusterPoint(point);            }

        }
        return self;
    }
}