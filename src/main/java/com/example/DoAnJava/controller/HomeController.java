package com.example.DoAnJava.controller;

import com.example.DoAnJava.entity.Contact;
import com.example.DoAnJava.entity.Product;
import org.antlr.v4.runtime.ParserRuleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping
    public String home(Model model)
    {
        String url = "http://shoptopping-89b153dfa8dc.herokuapp.com/product/list";
        List products = this.restTemplate.getForObject(url, List.class);
        model.addAttribute("products",products);
        return  "home/index";
    }

    @GetMapping("/products")
    public String products(Model model) {
        String url = "http://shoptopping-89b153dfa8dc.herokuapp.com/api/product/list";
        RestTemplate restTemplate = new RestTemplate();
        List result = restTemplate.getForObject(url, List.class);
        model.addAttribute("products", result);
        return "product/list";
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable(value = "id") Long id , Model model) {
        String url = "http://shoptopping-89b153dfa8dc.herokuapp.com/api/product/detail/"+id;
        RestTemplate restTemplate = new RestTemplate();
        Product result = restTemplate.getForObject(url, Product.class);
        model.addAttribute("product", result);
        return "product/detail";
    }

    @GetMapping("/contact")
    public String showContactForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "product/detail";
    }
    @PostMapping("/contact")
    public String submitContactForm(@ModelAttribute("contact") Contact contact, Model model) {
        model.addAttribute("contact", contact);
        return "product/detail";
    }
}
