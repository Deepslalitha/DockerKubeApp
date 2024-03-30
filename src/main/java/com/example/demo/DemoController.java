package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {


    @GetMapping("/")
    public String home() {
        return "This is a Test Project for Jenkins- Docker-Kube";
    }


}
