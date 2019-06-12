package com.map;

import com.entity.YearBase;
import com.utils.DateUtils;
import com.utils.HbaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 10:50
 */
public class YearBaseMap implements MapFunction<String, YearBase> {
    @Override
    public YearBase map(String s) throws Exception {
        if (StringUtils.isBlank(s)) {
            return null;
        }

        String[] userInfos = s.split(",");

        String userid = userInfos[0];
        String username = userInfos[1];
        String sex = userInfos[2];
        String telphone = userInfos[3];
        String email = userInfos[4];
        String age = userInfos[5];
        String registerTime = userInfos[6];
        String useType = userInfos[7];// 终端类型：0：PC端，1：移动端，2：小程序端

        String yearBaseType = DateUtils.getYearBaseByAge(age);
        // hbase 操作
        String tableName = "userflaginfo"; // 表名
        String rowKey = userid;
        String familyname = "baseinfo";
        String column = "yearbase"; // 年代
        String data = yearBaseType; // 数据
        HbaseUtils.putdata(tableName, rowKey, familyname, column, data);

        YearBase yearBase = new YearBase();
        String groupfield = "yearbase=="+yearBaseType;
        yearBase.setYeartype(yearBaseType);
        yearBase.setCount(1l);
        yearBase.setGroupfield(groupfield);

        return yearBase;
    }
}
