package com.map;

import com.entity.CarrierInfo;
import com.utils.CarrierUtils;
import com.utils.HbaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 10:50
 */
public class CarrierMap implements MapFunction<String, CarrierInfo> {
    @Override
    public CarrierInfo map(String s) throws Exception {
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

        int carrierType = CarrierUtils.getCarrierByTel(telphone);
        String carrier = carrierType == 0 ? "未知运营商"
                : carrierType == 1 ? "移动用户"
                : carrierType == 2 ? "联通用户" : "电信用户";


        // hbase 操作
        String tableName = "userflaginfo"; // 表名
        String rowKey = userid;
        String familyname = "baseinfo";
        String column = "carrierinfo"; // 运营商
        String data = carrier; // 数据
        HbaseUtils.putdata(tableName, rowKey, familyname, column, data);

        CarrierInfo carrierInfo = new CarrierInfo();
        String groupfield = "carrierInfo==" + carrierType;
        carrierInfo.setCarrier(carrier);
        carrierInfo.setCount(1l);
        carrierInfo.setGroupfield(groupfield);
        return carrierInfo;
    }
}
