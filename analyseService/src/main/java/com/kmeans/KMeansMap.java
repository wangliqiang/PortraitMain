package com.kmeans;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.Random;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 10:50
 */
public class KMeansMap implements MapFunction<String, KMeansInfo> {
    @Override
    public KMeansInfo map(String s) throws Exception {
        if (StringUtils.isBlank(s)) {
            return null;
        }

        String[] temps = s.split(",");
        Random random = new Random();
        String variable1 = temps[0];
        String variable2 = temps[1];
        String variable3 = temps[2];

        KMeansInfo kMeansInfo = new KMeansInfo();
        kMeansInfo.setVariable1(variable1);
        kMeansInfo.setVariable2(variable2);
        kMeansInfo.setVariable3(variable3);
        kMeansInfo.setGroupbyfield("kmeans=="+random.nextInt(10));

        return kMeansInfo;
    }
}
