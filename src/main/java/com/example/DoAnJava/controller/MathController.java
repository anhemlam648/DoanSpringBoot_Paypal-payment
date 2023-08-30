package com.example.DoAnJava.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/diem")
public class MathController {

    @GetMapping("/show")
    public String Showmath(Model model){
        return "diem/math";
    }
}
