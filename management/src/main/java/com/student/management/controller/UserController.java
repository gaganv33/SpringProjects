package com.student.management.controller;

import com.student.management.entity.User;
import com.student.management.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.save(user);
    }

    // dummy student api
    @GetMapping("/student")
    public String Student() {
        return "student";
    }

    // dummy teacher api
    @GetMapping("/teacher")
    public String Teacher() {
        return "teacher";
    }

    // dummy admin api
    @GetMapping("/admin")
    public String Admin() {
        return "admin";
    }
}
