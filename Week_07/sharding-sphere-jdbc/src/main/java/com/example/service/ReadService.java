package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReadService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> read() {
        return jdbcTemplate.queryForList("select * from test");
    }

}
