package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class LoginController {
    /*First page after visiting launching server*/
    @GetMapping()
    public String homePage(){
        return "home";
    }

    /*Login page */
    @GetMapping("/login")
    public String authorization(){
        return "login";
    }

    /*Home page if presses logout*/
    @GetMapping("/logout")
    public String logOut(){
        return "home";
    }


}
