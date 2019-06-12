package com.task;

import com.entity.ChaoManAndWomenInfo;
import com.kafka.CustomWatermarkExtractor;
import com.kafka.KafkaEvent;
import com.kafka.KafkaEventSchema;
import com.map.ChaoManAndWomenByReduceMap;
import com.map.ChaoManAndWomenMap;
import com.reduce.ChaoManAndWomenReduce;
import com.reduce.ChaoManAndWomenFinalReduce;
import com.sink.ChaoManAndWomenSink;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

/**
 * @Description 潮男潮女
 * @Author wangliqiang
 * @Date 2019/6/3 10:48
 */
public class ChaoManAndWomenTask {
    public static void main(String[] args) throws Exception {

        args = new String[]{"-input-topic", "scanProductLog", "--bootstrap-server", "flink01:9092", "-zookeeper.connect", "flink01:2821", "--group.id", "userportrait"};

        final ParameterTool parameterTool = ParameterTool.fromArgs(args);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000));
        env.enableCheckpointing(5000);
        env.getConfig().setGlobalJobParameters(parameterTool);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<KafkaEvent> input = env.addSource(
                new FlinkKafkaConsumer010<>(
                        parameterTool.getRequired("input-topic"),
                        new KafkaEventSchema(),
                        parameterTool.getProperties())
                        .assignTimestampsAndWatermarks(new CustomWatermarkExtractor()));
        DataStream<ChaoManAndWomenInfo> chaoManAndWomenMap = input.flatMap(new ChaoManAndWomenMap());
        DataStream<ChaoManAndWomenInfo>  chaoManAndWomenReduce = chaoManAndWomenMap.keyBy("groupfield")
                .timeWindowAll(Time.seconds(2)).reduce(new ChaoManAndWomenReduce()).flatMap(new ChaoManAndWomenByReduceMap());

        DataStream<ChaoManAndWomenInfo>  finalChaoManAndWomenReduce = chaoManAndWomenReduce.keyBy("groupfield").reduce(new ChaoManAndWomenFinalReduce());

        finalChaoManAndWomenReduce.addSink(new ChaoManAndWomenSink());

        env.execute("ChaoManAndWomenTask execute");
    }
}
