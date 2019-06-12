package com.logic;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.Random;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 10:50
 */
public class LogicMap implements MapFunction<String, LogicInfo> {
    @Override
    public LogicInfo map(String s) throws Exception {
        if (StringUtils.isBlank(s)) {
            return null;
        }

        String[] temps = s.split(",");
        Random random = new Random();
        String variable1 = temps[0];
        String variable2 = temps[1];
        String variable3 = temps[2];
        String label = temps[3];

        LogicInfo logicInfo = new LogicInfo();
        logicInfo.setVariable1(variable1);
        logicInfo.setVariable2(variable2);
        logicInfo.setVariable3(variable3);
        logicInfo.setLabel(label);
        logicInfo.setGroupbyfield("logic=="+random.nextInt(10));

        return logicInfo;
    }
}
