package com.kmeans;

/**
 * @Description 计算
 * @Author wangliqiang
 * @Date 2019/6/6 14:27
 */
public class DistanceCompute {

    /**
     * 求欧式距离
     *
     * @param p1
     * @param p2
     * @return
     */
    public double getEuclideanDis(Point p1, Point p2) {
        double count_dis = 0;
        float[] p1_local_array = p1.getLocalArray();
        float[] p2_local_array = p2.getLocalArray();

        if (p1_local_array.length != p2_local_array.length) {
            throw new IllegalArgumentException("length of array must be equal!");
        }
        for (int i = 0; i < p1_local_array.length; i++) {
            count_dis += Math.pow(p1_local_array[i] - p2_local_array[i], 2);
        }
        return Math.sqrt(count_dis);
    }
}
