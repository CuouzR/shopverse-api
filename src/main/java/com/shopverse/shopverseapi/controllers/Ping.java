package com.shopverse.shopverseapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aping")
public class Ping {



    @GetMapping("/ping")
    public String ping() {

        return "Pong";
    }
}
