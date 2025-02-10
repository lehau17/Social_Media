package com.booking.controller.http;

import com.booking.application.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hi")
public class HelloWorld {
    @Autowired
    private HiService hiService;
    @GetMapping
    public String sayHello(){
        return this.hiService.hi();
    }
}
