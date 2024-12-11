package cn.newbeedaly.kafka.controller;

import cn.newbeedaly.kafka.kafka.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @PostMapping("/send")
    public void send(){
        kafkaTemplate.send("test", "hello", "world");
    }

}
