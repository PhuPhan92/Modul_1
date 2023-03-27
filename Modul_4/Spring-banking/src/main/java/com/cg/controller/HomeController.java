package com.cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("")
    public String ShowHomePage (Model model){
        String fullName ="F**k";
        model.addAttribute("fullName", fullName);

        return "home";
    }
    @RequestMapping("/temp")
    public String showTempPage() {
        return "/WEB-INF/temp.html";
    }
}
