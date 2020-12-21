package com.example.controller;

import com.example.service.ReadService;
import com.example.service.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private WriteService writeService;

    @Autowired
    private ReadService readService;

    @RequestMapping("/test")
    public List<Map<String, Object>> test(int data) {
        writeService.write(data);
        return readService.read();
    }
}
