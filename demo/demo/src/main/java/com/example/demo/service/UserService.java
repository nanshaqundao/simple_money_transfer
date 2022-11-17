package com.example.demo.service;

import com.example.demo.model.BankUser;

import java.util.List;

public interface UserService {
    public BankUser selectByUserName(String username);

    public List<BankUser> listAllUsers();

    public int updateBalance(String username, Double newBalance);
}
