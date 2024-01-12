package com.wapp.core.resources;

import com.wapp.core.models.UserModel;
import com.wapp.core.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/core/users")
public class UserResource {

    UserService userService = new UserService();

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel userModel) {
        return userService.registerUser(userModel);
    }

}
