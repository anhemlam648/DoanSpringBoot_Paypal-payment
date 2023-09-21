//package com.example.DoAnJava.controller;
//
//import com.example.DoAnJava.entity.Product;
//import com.example.DoAnJava.services.ProductService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//public class PayPallController {
//
//    @Autowired
//    private ProductService productService;
//    @PostMapping("/paypal-ipn")
//    public void handlePayPalIPN(HttpServletRequest request) {
//        // Xử lý thông báo PayPal IPN ở đây
//
//        String paymentStatus = request.getParameter("payment_status");
//        String productId = request.getParameter("custom"); // Lấy ID sản phẩm từ PayPal IPN
//
//        // Giả định bạn bán một sản phẩm mỗi lần
//        int purchasedQuantity = 1;
//
//        // Kiểm tra tình trạng thanh toán
//        if ("Completed".equals(paymentStatus)) {
//            // Tình trạng thanh toán là "Hoàn thành", có thể cập nhật số lượng sản phẩm
//            Product product = productService.getProductById(Long.parseLong(productId));
//            if (product != null) {
//                // Cập nhật số lượng sản phẩm sau khi thanh toán
//                int newStockQuantity = product.getQuantityStock() - purchasedQuantity;
//                product.setQuantityStock(newStockQuantity);
//
//                // Lưu thông tin sản phẩm đã cập nhật vào cơ sở dữ liệu
//                productService.updateSoLuong(product, product.getId());
//            } else {
//                // Xử lý trường hợp sản phẩm không tồn tại
//            }
//        }
//        // ...
//    }
//}
