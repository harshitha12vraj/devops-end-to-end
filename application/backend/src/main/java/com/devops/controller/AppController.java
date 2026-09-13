package com.devops.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AppController {

    @GetMapping("/")
    public String home() {
        return "DevOps End-to-End Project is running!";
    }

    @GetMapping("/api/status")
    public Map<String, String> status() {
        return Map.of(
                "application", "backend",
                "status", "UP",
                "environment", "development"
        );
    }

    @GetMapping("/api/version")
    public Map<String, String> version() {
        return Map.of(
                "version", "1.0.1"
        );
    }
}