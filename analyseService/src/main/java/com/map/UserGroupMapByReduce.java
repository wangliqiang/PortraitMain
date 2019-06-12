package com.map;

import com.entity.UserGroupInfo;
import com.utils.DateUtils;
import com.utils.ReadProperties;
import org.apache.flink.api.common.functions.MapFunction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 败家指数
 * @Author wangliqiang
 * @Date 2019/6/3 10:50
 */
public class UserGroupMapByReduce implements MapFunction<UserGroupInfo, UserGroupInfo> {

    @Override
    public UserGroupInfo map(UserGroupInfo userGroupInfo) throws Exception {

        List<UserGroupInfo> list = userGroupInfo.getList();

        // 排序---start
        Collections.sort(list, new Comparator<UserGroupInfo>() {
            @Override
            public int compare(UserGroupInfo o1, UserGroupInfo o2) {
                String timeo1 = o1.getCreatetime();
                String timeo2 = o2.getCreatetime();
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
                Date dateNow = new Date();
                Date time1 = dateNow;
                Date time2 = dateNow;
                try {
                    time1 = dateFormat.parse(timeo1);
                    time2 = dateFormat.parse(timeo2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return time1.compareTo(time2);
            }
        });
        // 排序---end
        double totalamount = 0d;// 总金额
        double maxamount = Double.MIN_VALUE;// 最大金额

        Map<Integer, Integer> frequencyMap = new HashMap<>();// 消费频次
        UserGroupInfo userGroupInfoBefore = null;

        Map<String, Long> productTypeMap = new HashMap<>();// 商品类别map
        productTypeMap.put("1",0l);
        productTypeMap.put("2",0l);
        productTypeMap.put("3",0l);

        Map<String, Long> timeMap = new HashMap<>();// 时间map
        timeMap.put("1",0l);
        timeMap.put("2",0l);
        timeMap.put("3",0l);
        timeMap.put("4",0l);


        for (UserGroupInfo groupInfo : list) {
            double totalamountdouble = Double.valueOf(groupInfo.getTotalamount());
            totalamount += totalamountdouble;
            if (totalamountdouble > maxamount) {
                maxamount = totalamountdouble;
            }
            if (userGroupInfoBefore == null) {
                userGroupInfoBefore = groupInfo;
                continue;
            }

            // 计算购买频率
            String beforeTime = userGroupInfoBefore.getCreatetime();
            String endTime = groupInfo.getCreatetime();
            int days = DateUtils.getDaysBetweenStartAndEnd(beforeTime, endTime, "yyyyMMdd hhmmss");
            int befo = frequencyMap.get(days) == null ? 0 : frequencyMap.get(days);
            frequencyMap.put(days, befo + 1);

            // 计算消费类目
            String productType = userGroupInfo.getProducttypeid();
            String bitProductType = ReadProperties.getKey(productType, "producttypedic.properties");

            Long pre = productTypeMap.get(bitProductType) == null ? 0l : productTypeMap.get(bitProductType);
            productTypeMap.put(productType, pre + 1);

            // 时间点
            String time = groupInfo.getCreatetime();
            Integer hours = Integer.valueOf(DateUtils.getHoursByDay(time));

            int timeType = -1;
            if (hours > 7 && hours < 12) {
                timeType = 1;
            } else if (hours > 12 && hours < 19) {
                timeType = 2;
            } else if (hours > 19 && hours < 24) {
                timeType = 3;
            } else if (hours > 0 && hours < 7) {
                timeType = 4;
            }

            Long timesPre = timeMap.get(timeType) == null ? 0l : timeMap.get(timeType);
            timeMap.put(String.valueOf(timeType),timesPre);
        }

        int orderNums = list.size();
        double averageAmount = totalamount;//平均消费金额
        double maxAmount = maxamount;//最大消费金额
        Set<Map.Entry<Integer, Integer>> set = frequencyMap.entrySet();
        Integer totalDays = 0;
        for (Map.Entry<Integer, Integer> map : set) {
            Integer days = map.getKey();
            Integer count = map.getValue();
            totalDays += days + count;
        }
        int days = totalDays / orderNums;// 消费频次

        Random random = new Random();


        UserGroupInfo userGroupInfoFinal = new UserGroupInfo();
        userGroupInfoFinal.setUserid(userGroupInfo.getUserid());
        userGroupInfoFinal.setAverageamount(averageAmount);
        userGroupInfoFinal.setMaxamount(maxamount);
        userGroupInfoFinal.setDays(days);
        userGroupInfoFinal.setBuytype1(productTypeMap.get("1"));
        userGroupInfoFinal.setBuytype2(productTypeMap.get("2"));
        userGroupInfoFinal.setBuytype3(productTypeMap.get("3"));
        userGroupInfoFinal.setBuytime1(timeMap.get(1));
        userGroupInfoFinal.setBuytime2(timeMap.get(2));
        userGroupInfoFinal.setBuytime3(timeMap.get(3));
        userGroupInfoFinal.setBuytime4(timeMap.get(4));
        userGroupInfoFinal.setGroupfield("usergroupkmeans=="+random.nextInt(100));
        return userGroupInfoFinal;
    }
}
