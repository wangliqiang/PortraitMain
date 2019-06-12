package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.entity.AnalyseResult;
import com.entity.ViewResultAnalyse;
import com.service.MongoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/11 15:56
 */
@RestController
@RequestMapping("mongoData")
@CrossOrigin
public class MongoDataViewController {

    @Autowired
    MongoDataService mongoDataService;

    @RequestMapping(value = "resultInfoView", method = RequestMethod.GET)
    public String resultInfoView(String type) {
        List<AnalyseResult> list = new ArrayList<>();
        switch (type) {
            case "yearBase":
                list = mongoDataService.searchYearBase();
                break;
            case "useType":
                list = mongoDataService.searchUseType();
                break;
            case "email":
                list = mongoDataService.searchemail();
                break;
            case "consumptionLevel":
                list = mongoDataService.searchConsumptionLevel();
                break;
            case "chao":
                list = mongoDataService.searchChao();
                break;
            case "carrier":
                list = mongoDataService.searchCarrier();
                break;
            case "brandLike":
                list = mongoDataService.searchBrandLike();
                break;
            default:
                break;
        }

        ViewResultAnalyse viewResultAnalyse = new ViewResultAnalyse();
        List<String> infolist = new ArrayList<>(); // 分组list；x轴的值
        List<Long> countlist = new ArrayList<>(); // 数量； y轴的值
        for (AnalyseResult analyseResult : list) {
            infolist.add(analyseResult.getInfo());
            countlist.add(analyseResult.getCount());
        }
        viewResultAnalyse.setInfolist(infolist);
        viewResultAnalyse.setCountlist(countlist);

        return JSONObject.toJSONString(viewResultAnalyse);
    }

}
