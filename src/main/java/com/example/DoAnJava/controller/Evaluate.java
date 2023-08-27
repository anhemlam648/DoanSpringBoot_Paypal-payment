package com.example.DoAnJava.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/danhgia")
public class Evaluate {

    @Autowired
    JavaMailSender javaMailSender;

    @GetMapping("/form") // Sử dụng GetMapping thay vì RequestMapping cho việc hiển thị form
    public String showForm() {
        return "danhgia/danhgia";
    }

    @PostMapping("/send") // Sử dụng PostMapping cho việc gửi email
    public String sendMail(@RequestParam("name") String name,
                           @RequestParam("email") String email,
                           @RequestParam("message") String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("vunghia467@gmail.com");
        msg.setSubject("Đánh giá");
        msg.setText("Tên: " + name + "\nTin nhắn: " + message);
        javaMailSender.send(msg);
        return "danhgia/result";
    }

}
