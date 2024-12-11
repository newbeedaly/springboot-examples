package cn.newbeedaly.log4j.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Log4jController {

    private static Logger log = LoggerFactory.getLogger(Log4jController.class);

    @RequestMapping("/log")
    public String index() {

        log.info("-------------start------------");
        log.error("------------error--------------");
        log.info("--------------end-------------");

        log.debug("debug ****************************");
        log.info("info ****************************");
        log.warn("warn ****************************");
        log.error("error ****************************");
        return "hello log4j";
    }
}

