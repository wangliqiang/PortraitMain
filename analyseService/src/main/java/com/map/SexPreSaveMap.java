package com.map;

import com.entity.SexPreInfo;
import com.logic.Logistic;
import com.utils.HbaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.ArrayList;
import java.util.Random;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 10:50
 */
public class SexPreSaveMap implements MapFunction<String, SexPreInfo> {

    private ArrayList<Double> weights = null;

    public SexPreSaveMap(ArrayList<Double> weights) {
        this.weights = weights;
    }

    @Override
    public SexPreInfo map(String s) throws Exception {
        if (StringUtils.isBlank(s)) {
            return null;
        }

        String[] sexPreInfo = s.split("\t");
        Random random = new Random();
        // 清洗数据
        int userid = Integer.valueOf(sexPreInfo[0]);
        long ordernum = Long.valueOf(sexPreInfo[1]);
        long orderfre = Long.valueOf(sexPreInfo[2]);
        int manclothes = Integer.valueOf(sexPreInfo[3]);
        int childclothes = Integer.valueOf(sexPreInfo[4]);
        int oldclothes = Integer.valueOf(sexPreInfo[5]);
        int womenclothes = Integer.valueOf(sexPreInfo[6]);
        double averageamount = Double.valueOf(sexPreInfo[7]);
        int producttimes = Integer.valueOf(sexPreInfo[8]);
        int label = Integer.valueOf(sexPreInfo[9]);

        ArrayList<String> as = new ArrayList<>();
        as.add(ordernum + "");
        as.add(orderfre + "");
        as.add(manclothes + "");
        as.add(childclothes + "");
        as.add(oldclothes + "");
        as.add(womenclothes + "");
        as.add(averageamount + "");
        as.add(producttimes + "");

        String sexFlag = Logistic.classifyVector(as, weights);

        String sex = sexFlag == "0" ? "女" : "男";

        // hbase 操作
        String tableName = "userflaginfo"; // 表名
        String rowKey = userid + "";
        String familyname = "baseinfo";
        String column = "sex";
        String data = sex;
        HbaseUtils.putdata(tableName, rowKey, familyname, column, data);


        return null;
    }
}
