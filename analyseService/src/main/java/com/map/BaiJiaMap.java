package com.map;

import com.entity.BaiJiaInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 败家指数
 * @Author wangliqiang
 * @Date 2019/6/3 10:50
 */
public class BaiJiaMap implements MapFunction<String, BaiJiaInfo> {
    @Override
    public BaiJiaInfo map(String s) throws Exception {
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

        BaiJiaInfo baiJiaInfo = new BaiJiaInfo();
        baiJiaInfo.setUserid(userid);
        baiJiaInfo.setCreatetime(createtime);
        baiJiaInfo.setAmount(amount);
        baiJiaInfo.setPaytype(paytype);
        baiJiaInfo.setPaytime(paytime);
        baiJiaInfo.setPaystatus(paystatus);
        baiJiaInfo.setCouponamount(couponamount);
        baiJiaInfo.setTotalamount(totalamount);
        baiJiaInfo.setRefundamount(refundamount);
        baiJiaInfo.setCount(Long.valueOf(num));
        String groupfield = "baijia==" + userid;
        baiJiaInfo.setGroupfield(groupfield);
        List<BaiJiaInfo> list = new ArrayList<BaiJiaInfo>();
        list.add(baiJiaInfo);
        baiJiaInfo.setList(list);
        return baiJiaInfo;
    }
}
