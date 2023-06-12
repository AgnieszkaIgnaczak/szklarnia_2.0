package com.szklarnia.szklarnia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello") // wyświetlanie "Hello" na endpoincie /hello => localhost:8080/hello w przeglądarce
public class HelloController {

    @GetMapping
    public String hello() {return "Hello world!";}

}
