package com.example.demo.service;

import com.example.demo.model.TransactionRecord;
import com.example.demo.model.TransactionType;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Component
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public TransactionRecord transferMoney(String sourceAccount, Double amount, String targetAccount, String token) throws Exception {

        User sourceUser = userService.selectByUserName(sourceAccount);
        if (!Objects.equals(sourceUser.getToken(), token)) {
            // Authentication failed
            throw new Exception("Authentication Failed");
        }

        if (amount > sourceUser.getBalance()) {
            throw new Exception("Not enough balance");
        }

        User targetUser = userService.selectByUserName(targetAccount);
        if (targetUser.equals(null)) {
            throw new Exception("target account not existing");
        }
        Double sourceOldAmount = sourceUser.getBalance();
        Double targetOldAmount = targetUser.getBalance();
        int update1 = userService.updateBalance(sourceAccount, sourceOldAmount - amount);
        int update2 = userService.updateBalance(targetAccount, targetOldAmount + amount);

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));

        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setUserName(sourceAccount);
        transactionRecord.setTransactionTime(timestamp);
        transactionRecord.setOldBalance(sourceOldAmount);
        transactionRecord.setTransactionAmount(amount);
        transactionRecord.setNewBalance(sourceOldAmount - amount);
        transactionRecord.setTargetAccount(targetAccount);
        transactionRecord.setTransactionType(TransactionType.Debit);
        int update3 = insertTransaction(transactionRecord);
        if (update3 != 1) {
            throw new Exception("Transaction Update Failed");
        }
        return transactionRecord;
    }


    public int insertTransaction(TransactionRecord transactionRecord) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO `TRANSACTION_RECORD` (USERNAME,TRANSACTION_TIME,OLD_BALANCE,TRANSACTION_AMOUNT,NEW_BALANCE,TARGET_ACCOUNT,TRANSACTION_TYPE) VALUES(?,?,?,?,?,?,?)";

        int result = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1, transactionRecord.getUserName());
            ps.setTimestamp(2, transactionRecord.getTransactionTime());
            ps.setDouble(3, transactionRecord.getOldBalance());
            ps.setDouble(4, transactionRecord.getTransactionAmount());
            ps.setDouble(5, transactionRecord.getNewBalance());
            ps.setString(6, transactionRecord.getTargetAccount());
            ps.setString(7, transactionRecord.getTransactionType());
            return ps;
        }, keyHolder);
        return result;
    }


}
