package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {


    @GetMapping("/home")
    public String index() {
        return "Myfirst Docker APPPPPPP!";
    }


}
