package com.example.DoAnJava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/google")
public class Google {

    @GetMapping("/show")
    public String showaddress(Model model){
        return "home/google";
    }
}
