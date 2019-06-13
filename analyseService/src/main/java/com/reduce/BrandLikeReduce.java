package com.reduce;

import com.entity.BrandLike;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/5 10:13
 */
public class BrandLikeReduce implements ReduceFunction<BrandLike> {
    @Override
    public BrandLike reduce(BrandLike brandLike, BrandLike t1) throws Exception {
        String brand = brandLike.getBrand();
        Long count1 = brandLike.getCount();

        Long count2 = t1.getCount();

        BrandLike finalBrandLike = new BrandLike();
        finalBrandLike.setBrand(brand);
        finalBrandLike.setCount(count1+count2);

        return finalBrandLike;
    }
}
