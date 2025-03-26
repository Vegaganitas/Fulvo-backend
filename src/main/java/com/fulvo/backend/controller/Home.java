package com.fulvo.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
@RequiredArgsConstructor
public class Home {
    @PostMapping(value = "teams")
    public String listTeams(){
        return "Welcome from secure endpoint";
    }
}
