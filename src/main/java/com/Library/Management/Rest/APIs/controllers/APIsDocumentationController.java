package com.Library.Management.Rest.APIs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class APIsDocumentationController {

    @GetMapping("")
    public String documentation(){
     return "redirect:/swagger-ui/index.html";
    }
}
