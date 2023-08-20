package com.example.DoAnJava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/postshow")
public class PostsController {

    @GetMapping("/post")
    public String postshow(){
        return "home/post";
    }
}
