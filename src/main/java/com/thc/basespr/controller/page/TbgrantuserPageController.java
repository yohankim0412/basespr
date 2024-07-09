package com.thc.basespr.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tbgrantuser")
@Controller
public class TbgrantuserPageController {
    @GetMapping("/{page}")
    public String page(@PathVariable("page") String page){
        return "tbgrantuser/" + page;
    }

    @GetMapping("/admin_list/{id}")
    public String aList(@PathVariable("id") String page){
        return "tbgrantuser/admin_list";
    }
    @GetMapping("/admin_create/{id}")
    public String aCreate(@PathVariable("id") String page){
        return "tbgrantuser/admin_create";
    }
    @GetMapping("/admin_detail/{id}")
    public String aDetail(@PathVariable("id") String page){
        return "tbgrantuser/admin_detail";
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") String page){
        return "tbgrantuser/detail";
    }
}
