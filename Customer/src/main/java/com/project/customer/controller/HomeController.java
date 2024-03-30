package com.project.customer.controller;

import com.project.library.model.Customer;
import com.project.library.model.ShoppingCart;
import com.project.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private CustomerService clientService;

    @GetMapping(value = {"/", "/index"})
    public String homepage(Model model, Principal princ, HttpSession sess){
        model.addAttribute("title", "Home");
        if(princ != null){
            Customer customer = clientService.findByUsername(princ.getName());
            sess.setAttribute("username", customer.getFirstName() + " " + customer.getLastName());
            ShoppingCart cart = customer.getCart();
            if(cart != null){
                sess.setAttribute("totalItems", cart.getTotalItems() );
            }
        }
        return "homepage";
    }

    @RequestMapping (value = {"/contact"}, method = RequestMethod.GET)
    public String contact(Model model){
        model.addAttribute("title", "Contact");
        return "contact-us";
    }

}
