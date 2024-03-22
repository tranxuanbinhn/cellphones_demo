package com.cellphones10.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class test {
    @GetMapping("/hello")
    public String test()
    {
        return "hello";
    }
}
