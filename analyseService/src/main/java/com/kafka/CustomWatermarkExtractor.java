package com.kafka;

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/5 9:13
 */
public class CustomWatermarkExtractor implements AssignerWithPeriodicWatermarks<KafkaEvent> {

    private static final long serialVersionID = 3793742846169732579L;

    private long currentTimestamp = Long.MIN_VALUE;

    @Nullable
    @Override
    public Watermark getCurrentWatermark() {
        return new Watermark(currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE : currentTimestamp - 1);
    }

    @Override
    public long extractTimestamp(KafkaEvent event, long previousElementTimestamp) {
        this.currentTimestamp = event.getTimestamp();
        return event.getTimestamp();
    }
}
