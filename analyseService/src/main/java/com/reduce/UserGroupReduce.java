package com.reduce;

import com.entity.UserGroupInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 败家指数
 * @Author wangliqiang
 * @Date 2019/6/3 11:48
 */
public class UserGroupReduce implements ReduceFunction<UserGroupInfo> {

    @Override
    public UserGroupInfo reduce(UserGroupInfo userGroupInfo, UserGroupInfo t1) throws Exception {
        String userid = userGroupInfo.getUserid();
        List<UserGroupInfo> userGroup1= userGroupInfo.getList();
        List<UserGroupInfo> userGroup2 = t1.getList();

        List<UserGroupInfo> finalList = new ArrayList<UserGroupInfo>();
        finalList.addAll(userGroup1);
        finalList.addAll(userGroup2);

        UserGroupInfo finalUserGroupInfo = new UserGroupInfo();
        finalUserGroupInfo.setUserid(userid);
        finalUserGroupInfo.setList(finalList);

        return finalUserGroupInfo;
    }
}
