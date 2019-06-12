package com.task;

import com.entity.BaiJiaInfo;
import com.map.BaiJiaMap;
import com.reduce.BaiJiaReduce;
import com.utils.DateUtils;
import com.utils.HbaseUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 败家指数
 * @Author wangliqiang
 * @Date 2019/6/3 10:48
 */
public class BaiJiaTask {
    public static void main(String[] args) throws Exception {
        ParameterTool params = ParameterTool.fromArgs(args);

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataSet<String> text = env.readTextFile(params.get("input"));
        DataSet<BaiJiaInfo> mapResult = text.map(new BaiJiaMap());

        DataSet<BaiJiaInfo> reduce = mapResult.groupBy("groupfield").reduce(new BaiJiaReduce());

        List<BaiJiaInfo> resultList = reduce.collect();

        for (BaiJiaInfo baiJiaInfo : resultList) {
            String userid = baiJiaInfo.getUserid();
            List<BaiJiaInfo> list = baiJiaInfo.getList();
            Collections.sort(list, new Comparator<BaiJiaInfo>() {
                @Override
                public int compare(BaiJiaInfo o1, BaiJiaInfo o2) {
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
            BaiJiaInfo before = null;
            Map<Integer, Integer> frequencyMap = new HashMap<>();// 购买频率
            double maxAmount = 0d;
            double sum = 0d;
            for (BaiJiaInfo baiJiaInfoInner : list) {
                if (before == null) {
                    before = baiJiaInfoInner;
                    continue;
                }
                // 计算购买频率
                String beforeTime = before.getCreatetime();
                String endTime = baiJiaInfoInner.getCreatetime();
                int days = DateUtils.getDaysBetweenStartAndEnd(beforeTime, endTime, "yyyyMMdd hhmmss");
                int befo = frequencyMap.get(days) == null ? 0 : frequencyMap.get(days);
                frequencyMap.put(days, befo + 1);

                // 计算最大金额
                String totalAmountStr = baiJiaInfoInner.getTotalamount();
                Double totalAmount = Double.valueOf(totalAmountStr);
                if (totalAmount > maxAmount) {
                    maxAmount = totalAmount;
                }

                // 计算平均值
                sum += totalAmount;

                before = baiJiaInfoInner;
            }

            double averageAmount = sum / list.size();
            int totalDays = 0;
            Set<Map.Entry<Integer, Integer>> set = frequencyMap.entrySet();
            for (Map.Entry<Integer, Integer> entry : set) {
                Integer frequencyDays = entry.getKey();
                Integer count = entry.getValue();
                totalDays = frequencyDays * count;
            }
            int averageDays = totalDays / list.size(); // 平均天数

            // 败家指数 = 支付金额平均值*0.3 、 最大支付金额*0.3 、 下单频率*0.4
            // 支付金额平均值计算
            int averageAmountScore = 0;
            if (averageAmount >= 0 && averageAmount < 20) {
                averageAmountScore = 5;
            } else if (averageAmount >= 20 && averageAmount < 60) {
                averageAmountScore = 10;
            } else if (averageAmount >= 60 && averageAmount < 100) {
                averageAmountScore = 20;
            } else if (averageAmount >= 100 && averageAmount < 150) {
                averageAmountScore = 30;
            } else if (averageAmount >= 150 && averageAmount < 200) {
                averageAmountScore = 40;
            } else if (averageAmount >= 200 && averageAmount < 250) {
                averageAmountScore = 60;
            } else if (averageAmount >= 250 && averageAmount < 350) {
                averageAmountScore = 70;
            } else if (averageAmount >= 350 && averageAmount < 450) {
                averageAmountScore = 80;
            } else if (averageAmount >= 450 && averageAmount < 600) {
                averageAmountScore = 90;
            } else if (averageAmount >= 600) {
                averageAmountScore = 100;
            }

            // 最大支付金额计算
            int maxAmountScore = 0;
            if (maxAmount >= 0 && maxAmount < 20) {
                maxAmountScore = 5;
            } else if (maxAmount >= 20 && maxAmount < 60) {
                maxAmountScore = 10;
            } else if (maxAmount >= 60 && maxAmount < 200) {
                maxAmountScore = 30;
            } else if (maxAmount >= 200 && maxAmount < 500) {
                maxAmountScore = 60;
            } else if (maxAmount >= 500 && maxAmount < 700) {
                maxAmountScore = 80;
            } else if (maxAmount >= 700) {
                maxAmountScore = 100;
            }

            // 下单频率计算
            int maxDaysScore = 0;
            if (averageDays>= 0 && averageDays < 5) {
                maxDaysScore = 100;
            } else if (averageDays >= 5 && averageDays < 10) {
                maxDaysScore = 90;
            } else if (averageDays >= 10 && averageDays < 30) {
                maxDaysScore = 70;
            } else if (averageDays >= 30 && averageDays < 60) {
                maxDaysScore = 60;
            } else if (averageDays >= 60 && averageDays < 80) {
                maxDaysScore = 40;
            } else if (averageDays >= 80 && averageDays < 100) {
                maxDaysScore = 20;
            } else if(averageDays>=100){
                maxDaysScore = 10;
            }

            double totalScore = (averageAmountScore/100)*30+(maxAmountScore/100)*30+(averageDays/100)*40;

            // hbase 操作
            String tableName = "userflaginfo"; // 表名
            String rowKey = userid;
            String familyname = "baseinfo";
            String column = "baijiascore"; // 运营商
            String data = totalScore+""; // 数据
            HbaseUtils.putdata(tableName, rowKey, familyname, column, data);
        }
        env.execute("BaiJiaTask execute!");

    }
}
