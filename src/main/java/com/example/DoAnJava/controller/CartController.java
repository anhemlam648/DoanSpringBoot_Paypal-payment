package com.example.DoAnJava.controller;

import com.example.DoAnJava.daos.Cart;
import com.example.DoAnJava.daos.CartItem;
import com.example.DoAnJava.entity.Location;
import com.example.DoAnJava.services.CartService;
import com.example.DoAnJava.services.LocationService;
import com.example.DoAnJava.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@SessionAttributes("cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session, Authentication authentication) {

        Cart cart = (Cart) model.getAttribute("cart");
//
//
        if (cart == null) {
            cart = new Cart();
            model.addAttribute("cart", cart);
        }
        //tạo biến đề cập đến người dùng đã đăng nhập
        var name = authentication.getName();
        var user = this.userService.findUserByUserName(name);
        model.addAttribute("totalPrice", cartService.getSumPrice(session));
        model.addAttribute("items", cart.getItems());
        model.addAttribute("userId", user.getId());
        List<Location> location = this.locationService.getAllLoaction();
        model.addAttribute("location", location);
        model.addAttribute("user", user);
        return "cart";
    }
//@GetMapping("/cart")
//public String viewCart(Model model, HttpSession session, Authentication authentication) {
//
//    Cart cart = (Cart) model.getAttribute("cart");
//
//    if (cart == null) {
//        cart = new Cart();
//        model.addAttribute("cart", cart);
//    }
//
//    if (authentication != null) {
//        var name = authentication.getName();
//        var user = this.userService.findUserByUserName(name);
//        model.addAttribute("userId", user.getId());
//        model.addAttribute("user", user);
//    }
//
//    model.addAttribute("totalPrice", cartService.getSumPrice(session));
//    model.addAttribute("items", cart.getItems());
//
//    List<Location> location = this.locationService.getAllLoaction();
//    model.addAttribute("location", location);
//
//    return "cart";
//}
    //@RequestMapping("/cart/add/{id}") id product
    //public String addCart(Cart cart=new Cart(),@RequestParam Long id, HttpSession session)
    //String url = "http://shoptopping-89b153dfa8dc.herokuapp.com/product/"+id;
    //ProductDto product = this.restTemplate.getForObject(url, ProductDto.class);
    //if (cart == null) {
    //            cart.add(product);
    //        }else{List<CartItem> items = cart.getItems();
    //            for (CartItem item : items) {
    //                if (item.getId().equals(id)) {
    //                    item.setQuantity(item.getQuantity() + quantity);
    //                    return "redirect:/cart";
    //                }
    //            }
    //}
    //phương thức sẽ xử lý cả yêu cầu HTTP GET và POST.
    @RequestMapping(value = "/cart/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addToCart(@RequestParam Long id, @RequestParam int quantity, @RequestParam String imageList, @RequestParam String name, @RequestParam BigDecimal price, Model model) {

        Cart cart = (Cart) model.getAttribute("cart");


        if (cart == null) {
            cart = new Cart();
            model.addAttribute("cart", cart);
        } else {

            List<CartItem> items = cart.getItems(); //đại diện cho mỗi sản phẩm trong giỏ hàng
            for (CartItem item : items) {
                if (item.getId().equals(id)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    return "redirect:/cart";
                }
            }
        }


        CartItem product = new CartItem(id, name, price, quantity, imageList); //tạo đối tượng CartItem mới với tham số được truyền vào
        CartItem products = new CartItem(id, name, price, quantity, imageList);
        cart.addItem(product);

        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(HttpSession session, @PathVariable Long id) {
        var cart = cartService.getCart(session); // lấy giỏ hàng
        cart.removeItem(id); // xóa đối tượng lấy từ giỏ hàng
        return "redirect:/cart";
    }
    @GetMapping("/cart/count")
    @ResponseBody
    public int getCartItemCount(Model model) {
        Cart cart = (Cart) model.getAttribute("cart");//lưu trữ trong model với tên là cart
        if (cart == null) {
            return 0;
        }
        return cart.getItems().size(); // nếu giỏ hàng tồn tại sẽ trả về số lượng mục giỏ hàng qua phương thức size
    }


}
