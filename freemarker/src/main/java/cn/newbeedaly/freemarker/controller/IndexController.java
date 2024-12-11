package cn.newbeedaly.freemarker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/freemarker")
public class IndexController {


    @RequestMapping(value = "/hello")
    public String index(ModelMap modelMap) {

        Map<String, String> map = new HashMap<>();
        map.put("name", "newbeedaly");
        map.put("desc", "描述");

        // 添加属性给模版
        modelMap.addAttribute("user", map);

        return "index";
    }

}
