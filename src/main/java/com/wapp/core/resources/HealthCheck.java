package com.wapp.core.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HealthCheck {

    @GetMapping("/healthCheck")
    public ResponseEntity<?> healthCheck() {
        System.out.println("API is up and running!");
        return ResponseEntity.ok("API is up and running!");
    }

}
