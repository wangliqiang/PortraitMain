package com.service;

import com.entity.AnalyseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/11 15:42
 */
@FeignClient(value = "searchInfo")
public interface MongoDataService {
    @RequestMapping(value = "mongoData/searchYearBase", method = RequestMethod.POST)
    public List<AnalyseResult> searchYearBase();

    @RequestMapping(value = "mongoData/searchUseType", method = RequestMethod.POST)
    public List<AnalyseResult> searchUseType();

    @RequestMapping(value = "mongoData/searchEmail", method = RequestMethod.POST)
    public List<AnalyseResult> searchemail();

    @RequestMapping(value = "mongoData/searchConsumptionLevel", method = RequestMethod.POST)
    public List<AnalyseResult> searchConsumptionLevel();

    @RequestMapping(value = "mongoData/searchChao", method = RequestMethod.POST)
    public List<AnalyseResult> searchChao();

    @RequestMapping(value = "mongoData/searchCarrier", method = RequestMethod.POST)
    public List<AnalyseResult> searchCarrier();

    @RequestMapping(value = "mongoData/searchBrandLike", method = RequestMethod.POST)
    public List<AnalyseResult> searchBrandLike();

}
