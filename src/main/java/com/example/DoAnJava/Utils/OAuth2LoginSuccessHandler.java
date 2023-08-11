//package com.example.DoAnJava.Utils;
//
//import com.example.DoAnJava.DTO.CreateUserDto;
//import com.example.DoAnJava.entity.User;
//import com.example.DoAnJava.repository.IUserRepository;
//import com.example.DoAnJava.services.CustomUserDetailService;
//import com.example.DoAnJava.services.UserService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
////@Component
////public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
////
////    @Autowired
////    private UserService userService;
////
////
////    @Override
////    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
////            throws IOException, ServletException {
////        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
////        String email = oAuth2User.getName();
////        System.out.println("Customer's email:" + email);
////        super.onAuthenticationSuccess(request, response, authentication);
////    }
////}
//@Component
//public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
//            throws IOException, ServletException {
//        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
//        String email = oAuth2User.getEmail(); // Sử dụng getEmail() để lấy email của người dùng
//        User existingUser = userService.findUserByUserName(oAuth2User.getName());
//
//        if (existingUser == null) {
//            // Người dùng chưa tồn tại, hãy tạo mới và lưu vào cơ sở dữ liệu
//            CreateUserDto newUser = new CreateUserDto();
//            newUser.setEmail(email);
//            newUser.setName(oAuth2User.getName()); // Sử dụng getName() để lấy tên của người dùng
//            newUser.setUsername(email); // Hoặc bạn có thể tạo một tên đăng nhập dựa trên email
//            // Set các thông tin khác của newUser (password, phoneNumber, role...)
//            userService.create(newUser);
//        }
//
//        // Tiếp tục xử lý đăng nhập thành công
//        super.onAuthenticationSuccess(request, response, authentication);
//    }
//}