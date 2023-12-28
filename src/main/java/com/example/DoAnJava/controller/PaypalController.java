package com.example.DoAnJava.controller;

import com.example.DoAnJava.DTO.PayPalOder;
import com.example.DoAnJava.daos.CartItem;
import com.example.DoAnJava.entity.Product;
import com.example.DoAnJava.repository.IOrderRepository;
import com.example.DoAnJava.services.*;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PaypalController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private PayPalService payPalService;

    @Autowired
    private OrderDetailService orderDetailService;


    @GetMapping("/paypal")
    public String getPaymentForm(@RequestParam(required = false) Long id, Model model, HttpSession session) {
        // Lấy tổng giá trị từ giỏ hàng và đặt giá trị vào model
//        BigDecimal totalAmount = cartService.getSumPrice(session);
//        model.addAttribute("totalAmount", totalAmount);
        // Lấy trị từng sản phẩm và đặt giá trị vào model
        BigDecimal productPrice = productService.getPriceForProduct(id);
        model.addAttribute("productPrice", productPrice);
        String productName = productService.getProductNameById(id);
        model.addAttribute("productName", productName);
        return "paypal/paypal";
    }

    //đánh dấu phương thức tham số payPalOder
    @PostMapping("/paypal")
    public String payment(@ModelAttribute("payPalOder") PayPalOder payPalOder, HttpSession session) {
        try {
            //chịu trách nhiệm tạo ra yêu cầu thanh toán PayPal dựa trên thông tin được cung cấp từ đối tượng payPalOder
            Payment payment = payPalService.createPayment(
                    payPalOder.getPrice(),
                    payPalOder.getCurrency(),
                    payPalOder.getMethod(),
                    payPalOder.getIntent(),
                    payPalOder.getDescription(),
                    "http://localhost:8080/payment/cancel",
                    "http://localhost:8080/payment/success");

//            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
//            if (cartItems != null) {
//                List<CartItem> itemsToRemove = cartItems.stream()
//                        .filter(item -> payPalOder.getProductId().contains(item.getId()))
//                        .collect(Collectors.toList());
//
//                // Xóa các sản phẩm đã mua khỏi giỏ hàng
////                cartItems.removeAll(itemsToRemove);
////                session.setAttribute("cartItems", cartItems);
//            }
            //Lấy URL của liên kết,duyệt qua các đối tượng payment
            for (Links link : payment.getLinks()) {
                System.out.println("Link: " + link.getHref() + ", Rel: " + link.getRel());
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    //ánh xạ tới trang cancel
    @GetMapping("/payment/cancel")
    public String cancelPay() {
        return "paypal/paypalcancel";
    }

    //chú thích liên kết paymentId,paymentId
    @GetMapping("/payment/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, Model model,HttpSession session) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);//thực hiện thanh toán từ paymentId,payerId
            System.out.println(payment.toJSON());//xuất thông tin về đối tượng json

            if (payment.getState().equals("approved")) { //kiểm tra đã thanh toán thành công
//                saveOrderAndOrderDetails(payment, session);
                return "paypal/paypalcheckout";
            }
        } catch (PayPalRESTException e) { //trả về trang cancel nếu thất bại
            System.out.println(e.getMessage());
        }
        return "redirect:/paypal/paypalcancel";
    }
//    private void saveOrderAndOrderDetails(Payment payment, HttpSession session) {
//        // Lấy thông tin giỏ hàng từ session
//        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
//
//        // Tạo đối tượng Order và lưu vào CSDL
//        Orders order = new Orders();
//        orderRepository.save(order);
//
//        // Lưu thông tin chi tiết đơn đặt hàng vào CSDL
//        for (CartItem cartItem : cartItems) {
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setOrders(order); // Đặt đơn hàng
//            orderDetail.setQuantity(cartItem.getQuantity());
//            orderDetailService.SaveOderDetail(orderDetail);
//        }
//
//        // Xóa giỏ hàng sau khi đã thanh toán thành công
//        session.removeAttribute("cartItems");
//    }

}

