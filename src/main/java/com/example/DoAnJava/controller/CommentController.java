package com.example.DoAnJava.controller;


import com.example.DoAnJava.entity.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private List<Comment> comments = new ArrayList<>();
    private Long nextId = 1L;

    @GetMapping("/chat")
    public String showCommentPage(Model model) {
        model.addAttribute("comments", comments);
        return "comment/showchat";
    }

    @PostMapping
    @ResponseBody
    public Comment addComment(@RequestBody Comment comment) {
        comment.setId(nextId);
        comments.add(comment);
        nextId++;
        return comment;
    }
//    @PostMapping("/delete-comment")
//    @ResponseBody
//    public ResponseEntity<String> deleteComment(@RequestBody Map<String, Long> request) {
//        Long id = request.get("id");
//        Comment commentToRemove = null;
//        for (Comment comment : comments) {
//            if (comment.getId().equals(id)) {
//                commentToRemove = comment;
//                break;
//            }
//        }
//        if (commentToRemove != null) {
//            comments.remove(commentToRemove);
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
//    }
}
