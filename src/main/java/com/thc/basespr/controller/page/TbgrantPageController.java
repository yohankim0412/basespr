package com.thc.basespr.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tbgrant")
@Controller
public class TbgrantPageController {
    @GetMapping("/{page}")
    public String page(@PathVariable("page") String page){
        return "tbgrant/" + page;
    }

    @GetMapping("/admin_detail/{id}")
    public String aDetail(@PathVariable("id") String page){
        return "tbgrant/admin_detail";
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") String page){
        return "tbgrant/detail";
    }
}
