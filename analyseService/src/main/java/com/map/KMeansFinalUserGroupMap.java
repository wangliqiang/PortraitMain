package com.map;

import com.alibaba.fastjson.JSONObject;
import com.entity.UserGroupInfo;
import com.kmeans.DistanceCompute;
import com.kmeans.Point;
import com.utils.HbaseUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 10:50
 */
public class KMeansFinalUserGroupMap implements MapFunction<UserGroupInfo, Point> {

    private List<Point> centers = new ArrayList<>();
    private DistanceCompute disC = new DistanceCompute();

    public KMeansFinalUserGroupMap(List<Point> centers) {
        this.centers = centers;
    }

    @Override
    public Point map(UserGroupInfo userGroupInfo) throws Exception {
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

        Point self = new Point(Integer.valueOf(userGroupInfo.getUserid()), f);
        float min_dis = Integer.MAX_VALUE;
        for (Point point : centers) {
            float tmp_dis = (float) Math.min(disC.getEuclideanDis(self, point), min_dis);
            if (tmp_dis != min_dis) {
                min_dis = tmp_dis;
                self.setClusterId(point.getId());
                self.setDist(min_dis);
                self.setClusterPoint(point);
            }
        }
        // hbase 操作
        String tableName = "userflaginfo"; // 表名
        String rowKey = self.getId() + "";
        String familyname = "usergroupinfo";
        String column = "usergroupinfo";// 用户分群信息
        String data = JSONObject.toJSONString(self);
        HbaseUtils.putdata(tableName, rowKey, familyname, column, data);

        return self;
    }
}
