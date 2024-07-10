package com.thc.basespr.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tbqna")
@Controller
public class TbqnaPageController {
    @GetMapping("/{page}")
    public String page(@PathVariable("page") String page){
        return "tbqna/" + page;
    }

    @GetMapping("/admin_detail/{id}")
    public String aDetail(@PathVariable("id") String page){
        return "tbqna/admin_detail";
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") String page){
        return "tbqna/detail";
    }
}
