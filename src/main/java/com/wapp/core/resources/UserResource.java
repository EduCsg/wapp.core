package com.wapp.core.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wapp.core.dto.UserDto;
import com.wapp.core.dto.UserMetadataDto;
import com.wapp.core.services.UserService;

import jakarta.validation.Valid;

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
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
		return userService.registerUser(userDto);
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
		return userService.loginUser(userDto);
	}

	@PostMapping("/metadata/{userId}")
	public ResponseEntity<?> updateUserMetadata(@PathVariable String userId,
			@RequestBody UserMetadataDto userMetadataDto) {
		return userService.insertUserMetadata(userId, userMetadataDto);
	}

}
