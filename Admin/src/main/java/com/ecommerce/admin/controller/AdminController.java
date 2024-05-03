package com.ecommerce.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminController {

    // ... other mappings ...
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/chart")
    public String chart() {
        return "charts";
    }

    @GetMapping("/tables")
    public String table() {
        return "tables";
    }

    @GetMapping("/404")
    public String error() {
        return "404";
    }

    @GetMapping("/utilities-color")
    public String utilitiesColor() {
        return "utilities-color";
    }

    @GetMapping("/utilities-border")
    public String utilitiesBorder() {
        return "utilities-border";
    }

    @GetMapping("/utilities-animation")
    public String utilitiesAnimation() {
        return "utilities-animation";
    }

    @GetMapping("/utilities-other")
    public String utilitiesOther() {
        return "utilities-other";
    }
}
