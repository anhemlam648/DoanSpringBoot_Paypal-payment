package com.example.DoAnJava.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @GetMapping("/zalo")
    public String Showchat(Model model){
        return "chat/chatzalo";
    }
}
