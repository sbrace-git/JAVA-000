package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class WriteService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void write(int data) {
        String sql = "insert into test (data) value (" + data + ")";
        jdbcTemplate.execute(sql);
    }
}
