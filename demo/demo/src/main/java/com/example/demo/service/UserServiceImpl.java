package com.example.demo.service;


import com.example.demo.model.BankUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public BankUser selectByUserName(String username) {
        return jdbcTemplate.queryForObject("Select * from BANK_USER where username = ?", getRowMapper(), username);
    }

    @Override
    public List<BankUser> listAllUsers() {
        return jdbcTemplate.query("select * from BANK_USER", getRowMapper());
    }

    @Override
    public int updateBalance(String username, Double newBalance) {
        return jdbcTemplate.update("UPDATE BANK_USER SET BALANCE = ? WHERE USERNAME =?", newBalance, username);
    }

    private RowMapper<BankUser> getRowMapper() {
        return new RowMapper<BankUser>() {
            @Override
            public BankUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                BankUser bankUser = new BankUser();
                bankUser.setId(rs.getInt("ID"));
                bankUser.setUserName(rs.getString("USERNAME"));
                bankUser.setBalance(rs.getDouble("BALANCE"));
                bankUser.setToken(rs.getString("TOKEN"));
                return bankUser;
            }
        };
    }
}
