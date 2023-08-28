package com.example.DoAnJava.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
public class HeartwarmingnewsController {


    @GetMapping("/show")
    public String shownews(Model model){
        return "tintuc/tamhuyet";
    }

    @GetMapping("/shows")
    public String shownew(Model model){
        return "tintuc/vigiontan";
    }

    @GetMapping("/showss")
    public String shownewss(Model model){
        return "tintuc/chaycungtocotoco";
    }

    @GetMapping("/showsss")
    public String shownewsss(Model model){
        return "tintuc/cungtocotoco";
    }
}
