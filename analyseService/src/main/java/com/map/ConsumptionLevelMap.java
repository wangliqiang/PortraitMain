package com.map;

import com.entity.ConsumptionLevelInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 10:50
 */
public class ConsumptionLevelMap implements MapFunction<String, ConsumptionLevelInfo> {
    @Override
    public ConsumptionLevelInfo map(String s) throws Exception {
        if (StringUtils.isBlank(s)) {
            return null;
        }

        String[] orderInfos = s.split(",");

        String id = orderInfos[0];
        String productid = orderInfos[1];
        String producttypeid = orderInfos[2];
        String createtime = orderInfos[3];
        String amount = orderInfos[4];
        String paytype = orderInfos[5];
        String paytime = orderInfos[6];
        String paystatus = orderInfos[7];
        String couponamount = orderInfos[8];
        String totalamount = orderInfos[9];
        String refundamount = orderInfos[10];
        String num = orderInfos[11];
        String userid = orderInfos[12];

        ConsumptionLevelInfo consumptionLevelInfo = new ConsumptionLevelInfo();
        consumptionLevelInfo.setUserid(userid);
        consumptionLevelInfo.setAmounttotal(totalamount);
        consumptionLevelInfo.setGroupfield("groupfield==" + userid);

        return consumptionLevelInfo;
    }
}
