package com.example.DoAnJava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chiase")
public class ShareController {

    @GetMapping("/show")
    public String Show(Model model){
        return "chiase/cungtocotoco";
    }
}
