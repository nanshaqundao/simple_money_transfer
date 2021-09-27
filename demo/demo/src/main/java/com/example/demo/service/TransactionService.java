package com.example.demo.service;

import com.example.demo.model.TransactionRecord;

import java.util.List;

public interface TransactionService {
    public TransactionRecord transferMoney(String sourceAccount, Double amount, String targetAccount, String token) throws Exception;
    public int insertTransaction(TransactionRecord transactionRecord);
    public List<TransactionRecord> listAllTransactionRecords();
}
