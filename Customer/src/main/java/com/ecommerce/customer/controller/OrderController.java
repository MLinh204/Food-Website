package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.OrderDetail;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;


    @GetMapping("/check-out")
    public String checkout(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        if (customer == null || customer.getPhoneNumber() == null || customer.getAddress() == null
                || customer.getCity() == null || customer.getCountry() == null
                || customer.getPhoneNumber().trim().isEmpty() || customer.getAddress().trim().isEmpty()
                || customer.getCity().trim().isEmpty() || customer.getCountry().trim().isEmpty()) {
            model.addAttribute("customer", customer);
            model.addAttribute("error", "You must fill the information before checkout!");
            return "account";
        } else {
            model.addAttribute("customer", customer);
            ShoppingCart cart = customer.getShoppingCart();
            model.addAttribute("cart", cart);
        }

        return "checkout";
    }


    @GetMapping("/order")
    public String order(Principal principal, Model model){
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        List<Order> orderList= customer.getOrders();
        if (orderList.isEmpty()) {
            model.addAttribute("check", "No orders found.");
        } else {
            model.addAttribute("orders", orderList);
        }
        return "order";
    }


    @GetMapping("/save-order")
    public String saveOrder(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart cart = customer.getShoppingCart();
        orderService.saveOrder(cart);
        return "redirect:/order";
    }

    @PostMapping("/order/accept")
    public String acceptOrder(@RequestParam("orderId") Long orderId) {
        orderService.acceptOrder(orderId);
        return "redirect:/order";
    }

    @PostMapping("/order/cancel")
    public String cancelOrder(@RequestParam("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/order";
    }



}
