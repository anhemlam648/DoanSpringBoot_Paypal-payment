package com.example.DoAnJava.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class Google {

    @GetMapping("/oauth2/callback/google")
    public String oauth2LoginCallback(@AuthenticationPrincipal OAuth2User principal) {
        return "redirect:/";
    }
}

