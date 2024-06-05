package com.wapp.core.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/core/auth")
public class AuthResource {

	@GetMapping("/")
	public ResponseEntity<?> validateToken() {
		return ResponseEntity.ok("Token válido!");
	}

}
