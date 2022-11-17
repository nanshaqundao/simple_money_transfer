package com.example.demo.service;

import com.example.demo.model.BankUser;
import com.example.demo.model.TransactionRecord;
import com.example.demo.model.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class TransactionServiceImpl implements TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public TransactionRecord transferMoney(String sourceAccount, Double amount, String targetAccount, String token) throws Exception {

        BankUser sourceBankUser = userService.selectByUserName(sourceAccount);
        if (!Objects.equals(sourceBankUser.getToken(), token)) {
            // Authentication failed
            logger.error("Authentication Failed");
            throw new Exception("Authentication Failed");
        }

        if (amount > sourceBankUser.getBalance()) {
            // not enough blance on the sending account
            logger.error("Not enough balance");
            throw new Exception("Not enough balance");
        }

        BankUser targetBankUser = userService.selectByUserName(targetAccount);
        if (targetBankUser.equals(null)) {
            // target account not existing
            logger.error("target account not existing");
            throw new Exception("target account not existing");
        }

        logger.info("--------Starting Transaction-------");
        Double sourceOldAmount = sourceBankUser.getBalance();
        Double targetOldAmount = targetBankUser.getBalance();
        int update1 = userService.updateBalance(sourceAccount, sourceOldAmount - amount);
        int update2 = userService.updateBalance(targetAccount, targetOldAmount + amount);

        logger.info("Sending from:" + sourceBankUser.getUserName() + " to " + targetBankUser.getUserName());
        logger.info("Transaction Amount is: " + amount);
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
            logger.error("Transaction Update Failed");
            throw new Exception("Transaction Update Failed");
        }

        logger.info("--------Finishing Transaction-------");
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

    @Override
    public List<TransactionRecord> listAllTransactionRecords() {
        return jdbcTemplate.query("select * from TRANSACTION_RECORD", getRowMapper());
    }

    private RowMapper<TransactionRecord> getRowMapper() {
        return new RowMapper<TransactionRecord>() {
            @Override
            public TransactionRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                TransactionRecord transactionRecord = new TransactionRecord();
                transactionRecord.setId(rs.getInt("ID"));
                transactionRecord.setUserName(rs.getString("USERNAME"));
                transactionRecord.setTransactionTime(rs.getTimestamp("TRANSACTION_TIME"));
                transactionRecord.setOldBalance(rs.getDouble("OLD_BALANCE"));
                transactionRecord.setTransactionAmount(rs.getDouble("TRANSACTION_AMOUNT"));
                transactionRecord.setNewBalance(rs.getDouble("NEW_BALANCE"));
                transactionRecord.setTargetAccount(rs.getString("TARGET_ACCOUNT"));
                transactionRecord.setTransactionType(rs.getString("TRANSACTION_TYPE"));
                return transactionRecord;
            }
        };
    }


}
