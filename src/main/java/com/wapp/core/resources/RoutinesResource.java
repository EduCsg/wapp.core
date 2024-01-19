package com.wapp.core.resources;

import com.wapp.core.services.RoutinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/core/routines")
public class RoutinesResource {

    @Autowired
    RoutinesService routinesService;

    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getRoutineByUserId(@PathVariable String userId) {
        return routinesService.getRoutineByUserId(userId);
    }

}
