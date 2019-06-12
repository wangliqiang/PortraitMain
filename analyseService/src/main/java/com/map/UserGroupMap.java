package com.map;

import com.entity.UserGroupInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 败家指数
 * @Author wangliqiang
 * @Date 2019/6/3 10:50
 */
public class UserGroupMap implements MapFunction<String, UserGroupInfo> {
    @Override
    public UserGroupInfo map(String s) throws Exception {
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

        UserGroupInfo userGroupInfo = new UserGroupInfo();
        userGroupInfo.setUserid(userid);
        userGroupInfo.setCreatetime(createtime);
        userGroupInfo.setAmount(amount);
        userGroupInfo.setPaytype(paytype);
        userGroupInfo.setPaytime(paytime);
        userGroupInfo.setPaystatus(paystatus);
        userGroupInfo.setCouponamount(couponamount);
        userGroupInfo.setTotalamount(totalamount);
        userGroupInfo.setRefundamount(refundamount);
        userGroupInfo.setProducttypeid(producttypeid);
        userGroupInfo.setCount(Long.valueOf(num));
        String groupfield = userid+"==userGroupinfo";
        userGroupInfo.setGroupfield(groupfield);
        List<UserGroupInfo> list = new ArrayList<UserGroupInfo>();
        list.add(userGroupInfo);
        userGroupInfo.setList(list);
        return userGroupInfo;
    }
}
