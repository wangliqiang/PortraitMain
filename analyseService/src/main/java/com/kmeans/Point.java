package com.kmeans;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description 点
 * @Author wangliqiang
 * @Date 2019/6/6 14:27
 */
public class Point {
    private float[] localArray;
    private int id;
    private int clusterId; // 标识属于哪个类中心
    private float dist; // 标识和所属类中心的距离
    private Point clusterPoint;// 中心点信息

    public Point(int id, float[] localArray) {
        this.id = id;
        this.localArray = localArray;
    }


    public Point(float[] localArray) {
        this.id = -1; // 表示不属于任意一个类
        this.localArray = localArray;
    }

    public float[] getLocalArray() {
        return localArray;
    }

    public void setLocalArray(float[] localArray) {
        this.localArray = localArray;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public float getDist() {
        return dist;
    }

    public void setDist(float dist) {
        this.dist = dist;
    }

    public Point getClusterPoint() {
        return clusterPoint;
    }

    public void setClusterPoint(Point clusterPoint) {
        this.clusterPoint = clusterPoint;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        if (point.localArray.length != localArray.length) {
            return false;
        }
        for (int i = 0; i < localArray.length; i++) {
            if (Float.compare(point.localArray[i], localArray[i]) != 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        float x = localArray[0];
        float y = localArray[localArray.length - 1];
        long temp = x != 0.0d ? Double.doubleToLongBits(x) : 0L;
        int result = (int) (temp ^ (temp >>> 32));
        temp = y != +0.0d ? Double.doubleToLongBits(y) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
