package com.search.controller;

import com.entity.AnalyseResult;
import com.search.service.MongoDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description TODO
 * 年代：yearbasestatics
 * 终端偏好：usetypestatics
 * 邮件运营商：emailstatics
 * 消费水平：consumptionlevelstatics
 * 潮男潮女：chaoManAndWomenstatics
 * 手机运营商：carrierstatics
 * 品牌偏好：brandlikestatics
 * @Author wangliqiang
 * @Date 2019/6/11 14:44
 */
@RestController
@RequestMapping("mongoData")
public class MongoDataController {

    @Autowired
    private MongoDataServiceImpl mongoDataService;

    @RequestMapping(value = "searchYearBase", method = RequestMethod.POST)
    public List<AnalyseResult> searchYearBase() {
        return mongoDataService.listMongoDataBy("yearbasestatics");
    }

    @RequestMapping(value = "searchUseType", method = RequestMethod.POST)
    public List<AnalyseResult> searchUseType() {
        return mongoDataService.listMongoDataBy("usetypestatics");
    }

    @RequestMapping(value = "searchEmail", method = RequestMethod.POST)
    public List<AnalyseResult> searchemail() {
        return mongoDataService.listMongoDataBy("emailstatics");
    }

    @RequestMapping(value = "searchConsumptionLevel", method = RequestMethod.POST)
    public List<AnalyseResult> searchConsumptionLevel() {
        return mongoDataService.listMongoDataBy("consumptionlevelstatics");
    }

    @RequestMapping(value = "searchChao", method = RequestMethod.POST)
    public List<AnalyseResult> searchChao() {
        return mongoDataService.listMongoDataBy("chaoManAndWomenstatics");
    }

    @RequestMapping(value = "searchCarrier", method = RequestMethod.POST)
    public List<AnalyseResult> searchCarrier() {
        return mongoDataService.listMongoDataBy("carrierstatics");
    }

    @RequestMapping(value = "searchBrandLike", method = RequestMethod.POST)
    public List<AnalyseResult> searchBrandLike() {
        return mongoDataService.listMongoDataBy("brandlikestatics");
    }
}
