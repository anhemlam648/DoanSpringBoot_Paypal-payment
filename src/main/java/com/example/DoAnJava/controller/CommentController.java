package com.example.DoAnJava.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private List<String> comments = new ArrayList<>();

    @GetMapping("/comments")
    public String showComments(Model model) {
        model.addAttribute("comments", comments);
        return "home/contact";
    }

    @PostMapping("/comments")
    public String addComment(@RequestParam String message) {
        comments.add(message);
        return "home/index"; // Chuyển hướng để hiển thị danh sách bình luận mới
    }
}
