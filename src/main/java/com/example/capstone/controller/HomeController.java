package com.example.capstone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "LoginSignup/index";
    }

    @GetMapping("/Resi/index")
    public String Resi_index() {
        return "/Resi/index.html";
    }

    @GetMapping("/Resi/inner_page")
    public String Resi_inner_page() {
        return "/Resi/inner_page.html";
    }
}