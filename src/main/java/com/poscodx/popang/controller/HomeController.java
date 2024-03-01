package com.poscodx.popang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String MoveLoginPage(){
        return "index";
    }
}
