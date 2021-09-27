package com.example.demo.service;

import com.example.demo.model.TransactionRecord;

import javax.servlet.http.HttpServletRequest;

public interface TransactionService {
    public TransactionRecord transferMoney(String sourceAccount, Double amount, String targetAccount, String token) throws Exception;
    public int insertTransaction(TransactionRecord transactionRecord);
}
