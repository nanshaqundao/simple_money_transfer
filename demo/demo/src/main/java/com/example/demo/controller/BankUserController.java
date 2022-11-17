package com.example.demo.controller;

import com.example.demo.model.BankUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BankUserController {


    @Autowired
    private UserService userService;

    @GetMapping("/selectByUserName")
    public BankUser selectByUserName(@RequestParam("userName") String username) {
        return userService.selectByUserName(username);
    }

    @GetMapping("/listAllUsers")
    public List<BankUser> listAllUsers() {
        return userService.listAllUsers();
    }
}