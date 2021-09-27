package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/selectByUserName")
    public User selectByUserName(@RequestParam("userName") String username) {
        return userService.selectByUserName(username);
    }

    @GetMapping("/listAllUsers")
    public List<User> listAllUsers() {
        return userService.listAllUsers();
    }
}