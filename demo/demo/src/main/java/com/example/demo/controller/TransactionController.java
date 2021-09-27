package com.example.demo.controller;

import com.example.demo.model.TransactionRecord;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transferMoney")
    public TransactionRecord transferMoney(@RequestParam("sourceAccount") String sourceAccount, @RequestParam("amount") String amount,
                                           @RequestParam("targetAccount") String targetAccount, @RequestParam("token") String token, HttpServletRequest request) throws Exception {

        return transactionService.transferMoney(sourceAccount, Double.valueOf(amount), targetAccount, token);

    }
}
