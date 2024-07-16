package com.woowahanrabbits.battle_people.domain.user.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    @GetMapping
    public List<?> getAllUsers() {
        return null;
    }

}
