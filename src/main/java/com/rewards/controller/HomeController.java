package com.rewards.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    
    @GetMapping("/login")
    public String home() {
    	System.out.println("In home login");
        return "customerdashboard";
    }
}