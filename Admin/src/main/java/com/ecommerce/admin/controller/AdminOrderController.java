package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Order;
import com.ecommerce.library.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

    @GetMapping("/orders")
    public String orders(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Order> orders = adminOrderService.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("size", orders.size());
        model.addAttribute("title", "Orders");
        return "orders";
    }

    @PostMapping("/accept-order")
    public String acceptOrder(@RequestParam("orderId") Long orderId, RedirectAttributes attributes) {
        try {
            adminOrderService.acceptOrder(orderId);
            attributes.addFlashAttribute("success", "Order accepted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to accept order");
        }
        return "redirect:/orders";
    }

    @PostMapping("/reject-order")
    public String rejectOrder(@RequestParam("orderId") Long orderId, RedirectAttributes attributes) {
        try {
            adminOrderService.rejectOrder(orderId);
            attributes.addFlashAttribute("success", "Order rejected successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to reject order");
        }
        return "redirect:/orders";
    }
}
