package com.example.demo.service;


import com.example.demo.model.User;
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
    public User selectByUserName(String username) {
        return jdbcTemplate.queryForObject("Select * from USER where username = ?", getRowMapper(), username);
    }

    @Override
    public List<User> listAllUsers() {
        return jdbcTemplate.query("select * from USER", getRowMapper());
    }

    @Override
    public int updateBalance(String username, Double newBalance) {
        return jdbcTemplate.update("UPDATE USER SET BALANCE = ? WHERE USERNAME =?", newBalance, username);
    }

    private RowMapper<User> getRowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setUserName(rs.getString("USERNAME"));
                user.setBalance(rs.getDouble("BALANCE"));
                user.setToken(rs.getString("TOKEN"));
                return user;
            }
        };
    }
}
