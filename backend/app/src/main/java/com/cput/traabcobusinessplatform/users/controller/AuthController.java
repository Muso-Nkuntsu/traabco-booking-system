package com.cput.traabcobusinessplatform.users.controller;
/**
 * Muso Nkuntsu
 * */


import com.cput.traabcobusinessplatform.users.dto.LoginRequest;
import com.cput.traabcobusinessplatform.users.dto.RegisterRequest;
import com.cput.traabcobusinessplatform.users.dto.UserResponse;
import com.cput.traabcobusinessplatform.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles public authentication endpoints.
 * No token is required to hit these — they are
 * the entry point for getting a token in the first place.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.loginUser(request));
    }


}
