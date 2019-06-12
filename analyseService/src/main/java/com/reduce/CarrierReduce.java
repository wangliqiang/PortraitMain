package com.reduce;

import com.entity.CarrierInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 11:48
 */
public class CarrierReduce implements ReduceFunction<CarrierInfo> {

    @Override
    public CarrierInfo reduce(CarrierInfo carrierInfo, CarrierInfo t1) throws Exception {
        String carrier = carrierInfo.getCarrier();
        Long count1 = carrierInfo.getCount();

        Long count2 = t1.getCount();

        CarrierInfo finalCarrier = new CarrierInfo();
        finalCarrier.setCarrier(carrier);
        finalCarrier.setCount(count1 + count2);
        return finalCarrier;
    }
}
