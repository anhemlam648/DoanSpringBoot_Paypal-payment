package com.example.DoAnJava.controller;


import com.example.DoAnJava.entity.User;
import com.example.DoAnJava.repository.IUserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/danhgia")
public class Evaluate {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    JavaMailSender javaMailSender;

    @GetMapping("/form") // Sử dụng GetMapping thay vì RequestMapping cho việc hiển thị form
    public String showForm() {
        return "danhgia/danhgia";
    }

//    @PostMapping("/send") // Sử dụng PostMapping cho việc gửi email
//    public String sendMail(@RequestParam("name") String name,
//                           @RequestParam("email") String email,
//                           @RequestParam("message") String message) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo("vunghia467@gmail.com");
////        List<User> users = userRepository.findByEmail(email);
//        msg.setSubject("Đánh giá");
////        String imageUrl = "https://drive.google.com/uc?id=https://drive.google.com/file/d/1dgauz28w5v4FaSJXoqtCTKRBw78jT6fM/view?usp=sharing";
//        String imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRUoRHrjFBpwG6fRkzlzkVtTet_mhKCE7qdoUxp3GC1I33ggCHKO6v5csmcjPCJjpXKM_E&usqp=CAU";
////        String base64Image = convertImageToBase64(imageUrl)
//        String emailcontent = "\nTên: " + name + "\nEmail: " + email + "\nTin nhắn: " + message +
//                "\nỨng dụng TocoToco";
//
////        msg.setText("Tên: " + name + "\nEmail: " + email + "\nTin nhắn: " + message);
//
//        msg.setText(emailcontent);
//        javaMailSender.send(msg);
//        return "danhgia/result";
//
//
//    }
    //Sử dụng để liên kết giá trị của một tham số với một tham số trong yêu cầu HTTP.
        @PostMapping("/send")
        public String sendMail(@RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("message") String message) {
            //MimeMessage là một đối tượng mô tả một email có thể chứa nhiều loại nội dung khác nhau như văn bản, hình ảnh, và tệp đính kèm.
            MimeMessage mimeMessage = javaMailSender.createMimeMessage(); //một đối tượng của interface JavaMailSender cho việc gửi gmail
            MimeMessageHelper msg; //MimeMessageHelper được sử dụng để giúp cấu hình thông tin về email,...
            try {
                msg = new MimeMessageHelper(mimeMessage, true);
                msg.setTo("vunghia467@gmail.com");
                msg.setSubject("Đánh giá"); //Tiêu đề
                String imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRUoRHrjFBpwG6fRkzlzkVtTet_mhKCE7qdoUxp3GC1I33ggCHKO6v5csmcjPCJjpXKM_E&usqp=CAU";
                String emailContent = "<html><body>"
                        + "<img src='" + imageUrl + "' alt='Hình ảnh'/>"
                        + "<p><b>Tên:</b> " + name + "</p>"
                        + "<p><b>Email:</b> " + email + "</p>"
                        + "<p><b>Tin nhắn:</b> " + message + "</p>"
                        + "<p><b>Ứng dụng TocoToco</b></p>"
                        + "</body></html>";

                msg.setText(emailContent, true);
                javaMailSender.send(mimeMessage);//gửi email
            } catch (MessagingException e) {
                // Xử lý ngoại lệ nếu cần
                e.printStackTrace();
            }

            return "danhgia/result";
        }

//    @PostMapping("/send")
//    public String sendMail(@RequestParam("name") String name,
//                           @RequestParam("message") String message) {
//
//        // Lấy thông tin về người dùng đã đăng nhập
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // Kiểm tra xem người dùng đã đăng nhập hay chưa
//        if (authentication != null && authentication.isAuthenticated()) {
//            // Lấy địa chỉ email của người dùng từ thông tin xác thực
//            String userEmail = authentication.getName();
//
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper msg;
//            try {
//                msg = new MimeMessageHelper(mimeMessage, true);
//                msg.setTo("vunghia467@gmail.com");
//                msg.setSubject("Đánh giá");
//                String imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRUoRHrjFBpwG6fRkzlzkVtTet_mhKCE7qdoUxp3GC1I33ggCHKO6v5csmcjPCJjpXKM_E&usqp=CAU";
//                String emailContent = "<html><body>"
//                        + "<img src='" + imageUrl + "' alt='Hình ảnh'/>"
//                        + "<p><b>Tên:</b> " + name + "</p>"
//                        + "<p><b>Email:</b> " + userEmail + "</p>"
//                        + "<p><b>Tin nhắn:</b> " + message + "</p>"
//                        + "<p><b>Ứng dụng TocoToco</b></p>"
//                        + "</body></html>";
//
//                msg.setText(emailContent, true);
//                javaMailSender.send(mimeMessage);
//            } catch (MessagingException e) {
//                // Xử lý ngoại lệ nếu cần
//                e.printStackTrace();
//            }
//
//            return "danhgia/result";
//        } else {
//            // Người dùng chưa đăng nhập, xử lý theo yêu cầu của bạn
//            return "redirect:/login"; // Hoặc chuyển hướng đến trang đăng nhập
//        }
//    }
}
