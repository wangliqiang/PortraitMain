package com.reduce;

import com.entity.SexPreInfo;
import com.logic.CreateDataSet;
import com.logic.Logistic;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/3 11:48
 */
public class SexPreReduce implements GroupReduceFunction<SexPreInfo, ArrayList<Double>> {


    @Override
    public void reduce(Iterable<SexPreInfo> iterable, Collector<ArrayList<Double>> collector) throws Exception {
        Iterator<SexPreInfo> iterator = iterable.iterator();
        CreateDataSet trainingSet = new CreateDataSet();
        while (iterator.hasNext()) {
            SexPreInfo sexPreInfo = iterator.next();
            int userid = sexPreInfo.getUserid();
            long ordernum = sexPreInfo.getOrdernum();
            long orderfre = sexPreInfo.getOrderfre();
            int manclothes = sexPreInfo.getManclothes();
            int childclothes = sexPreInfo.getChildclothes();
            int oldclothes = sexPreInfo.getOldclothes();
            int womenclothes = sexPreInfo.getWomenclothes();
            double averageamount = sexPreInfo.getAverageamount();
            int producttimes = sexPreInfo.getProducttimes();
            int label = sexPreInfo.getLabel();


            ArrayList<String> as = new ArrayList<>();
            as.add(ordernum+"");
            as.add(orderfre+"");
            as.add(manclothes+"");
            as.add(childclothes+"");
            as.add(oldclothes+"");
            as.add(womenclothes+"");
            as.add(averageamount+"");
            as.add(producttimes+"");
            as.add(label+"");
            trainingSet.data.add(as);
            trainingSet.labels.add(label+"");
        }
        ArrayList<Double> weights = new ArrayList<>();
        weights = Logistic.gradAscent1(trainingSet, trainingSet.labels, 500);
        collector.collect(weights);
    }
}
