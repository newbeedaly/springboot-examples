package cn.newbeedaly.logback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/logback")
@RestController
public class LogbackController {

    @GetMapping("/getLog")
    public void getLog() {
        log.info("info日志");
    }
}
