package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/4 15:10
 */
@RestController
@RequestMapping("test")
public class Test {

    @RequestMapping(value = "helloworld", method = RequestMethod.GET)
    public String helloworld(HttpServletRequest req) {
        String ip = req.getRemoteAddr();
        String result = "hello world===" + ip;
        return result;
    }

}
