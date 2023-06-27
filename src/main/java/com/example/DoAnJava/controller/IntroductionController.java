package com.example.DoAnJava.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/introduction")
public class IntroductionController {

    @GetMapping("/showintroduction")
    public String Callshow(){
        return "introduction/introduction";
    }
}
