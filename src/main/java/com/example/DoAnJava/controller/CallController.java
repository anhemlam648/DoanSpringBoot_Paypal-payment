package com.example.DoAnJava.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/callshow")
public class CallController {

    @GetMapping("/call")
    public String Callshow(){
        return "call/call";
    }
}
