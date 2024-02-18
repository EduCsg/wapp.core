package com.wapp.core.resources;

import com.wapp.core.dto.UserMetadataDto;
import com.wapp.core.models.UserModel;
import com.wapp.core.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/core/users")
public class UserResource {

    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserModel userModel) {
        return userService.registerUser(userModel);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserModel userModel) {
        return userService.loginUser(userModel);
    }

    @PostMapping("/metadata/{userId}")
    public ResponseEntity<?> updateUserMetadata(@PathVariable String userId, @RequestBody UserMetadataDto userMetadataDto) {
        return userService.insertUserMetadata(userId, userMetadataDto);
    }

}
