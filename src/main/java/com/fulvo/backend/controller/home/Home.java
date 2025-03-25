package com.fulvo.backend.controller.home;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
@RequiredArgsConstructor
public class Home {
    @PostMapping(value = "main")
    public String welcome(){
        return "Welcome from secure endpoint";
    }
}
