package com.map;

import com.entity.SexPreInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.Random;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 10:50
 */
public class SexPreMap implements MapFunction<String, SexPreInfo> {
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

        String fieldGroup = "sexpre==" + random.nextInt(10);
        SexPreInfo sexInfo = new SexPreInfo();

        sexInfo.setUserid(userid);
        sexInfo.setOrdernum(ordernum);
        sexInfo.setOrderfre(orderfre);
        sexInfo.setManclothes(manclothes);
        sexInfo.setChildclothes(childclothes);
        sexInfo.setOldclothes(oldclothes);
        sexInfo.setWomenclothes(womenclothes);
        sexInfo.setAverageamount(averageamount);
        sexInfo.setProducttimes(producttimes);
        sexInfo.setLabel(label);
        sexInfo.setGroupfield(fieldGroup);
        return sexInfo;
    }
}
