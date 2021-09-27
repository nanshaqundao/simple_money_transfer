package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    public User selectByUserName(String username);

    public List<User> listAllUsers();

    public int updateBalance(String username, Double newBalance);
}
