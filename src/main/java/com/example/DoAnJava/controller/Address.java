package com.example.DoAnJava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/address")
public class Address {

    @GetMapping("/show")
    public String showaddress(Model model){
        return "address/address";
    }
}
