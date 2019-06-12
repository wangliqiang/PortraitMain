package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.entity.ResultMessage;
import com.log.AttentionProductLog;
import com.log.BuyCartProductLog;
import com.log.CollectProductLog;
import com.log.ScanProductLog;
import com.utils.ReadProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/4 15:10
 */
@RestController
@RequestMapping("infolog")
public class InfoInController {

    private final String attentionProductLogTopic = ReadProperties.getKey("attentionProductLog");
    private final String buyCartProductLogTopic = ReadProperties.getKey("buyCartProductLog");
    private final String collectProductLogTopic = ReadProperties.getKey("collectProductLog");
    private final String scanProductLogTopic = ReadProperties.getKey("scanProductLog");

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(value = "helloworld", method = RequestMethod.GET)
    public String helloworld(HttpServletRequest req) {
        String ip = req.getRemoteAddr();
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setMessage("hello:" + ip);
        resultMessage.setStatus("success");
        String result = JSONObject.toJSONString(resultMessage);
        return result;
    }

    /**
     * AttentionProductLog:{produceid:produceid...}
     * BuyCartProductLog
     * CollectProductLog
     * ScanProductLog
     *
     * @param receivelog
     * @param req
     * @return
     */
    @RequestMapping(value = "receivelog", method = RequestMethod.POST)
    public String AttentionProductLog(String receivelog, HttpServletRequest req) {

        if (StringUtils.isBlank(receivelog)) {
            return null;
        }

        String[] receives = receivelog.split(":", 2);
        String className = receives[0];
        String data = receives[1];
        String message = "";


        if ("AttentionProductLog".equals(className)) {
            AttentionProductLog attentionProductLog = JSONObject.parseObject(data, AttentionProductLog.class);
            String ip = req.getRemoteAddr();
            attentionProductLog.setIp(ip);
            message = JSONObject.toJSONString(attentionProductLog);
            kafkaTemplate.send(attentionProductLogTopic, message + "##1##" + new Date().getTime());
        } else if ("BuyCartProductLog".equals(className)) {
            BuyCartProductLog buyCartProductLog = JSONObject.parseObject(data, BuyCartProductLog.class);
            String ip = req.getRemoteAddr();
            buyCartProductLog.setIp(ip);
            message = JSONObject.toJSONString(buyCartProductLog);
            kafkaTemplate.send(buyCartProductLogTopic, message + "##1##" + new Date().getTime());
        } else if ("CollectProductLog".equals(className)) {
            CollectProductLog collectProductLog = JSONObject.parseObject(data, CollectProductLog.class);
            String ip = req.getRemoteAddr();
            collectProductLog.setIp(ip);
            message = JSONObject.toJSONString(collectProductLog);
            kafkaTemplate.send(collectProductLogTopic, message + "##1##" + new Date().getTime());
        } else if ("ScanProductLog".equals(className)) {
            ScanProductLog scanProductLog = JSONObject.parseObject(data, ScanProductLog.class);
            String ip = req.getRemoteAddr();
            scanProductLog.setIp(ip);
            message = JSONObject.toJSONString(scanProductLog);
            kafkaTemplate.send(scanProductLogTopic, message + "##1##" + new Date().getTime());
        }
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setMessage(message);
        resultMessage.setStatus("success");
        String result = JSONObject.toJSONString(resultMessage);
        return result;
    }
}
