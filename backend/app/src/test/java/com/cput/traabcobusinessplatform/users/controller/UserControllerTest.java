package com.cput.traabcobusinessplatform.users.controller;


import com.cput.traabcobusinessplatform.config.JwtUtil;
import com.cput.traabcobusinessplatform.config.SecurityConfig;
import com.cput.traabcobusinessplatform.users.domain.enums.UserRole;
import com.cput.traabcobusinessplatform.users.dto.LoginRequest;
import com.cput.traabcobusinessplatform.users.dto.RegisterRequest;
import com.cput.traabcobusinessplatform.users.dto.UserResponse;
import com.cput.traabcobusinessplatform.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Muso Nkuntsu
 * Integration tests for AuthController and UserController.
 * Spins up the web layer only — service is mocked.
 * Tests HTTP status codes, request validation, and role access.
 */

@WebMvcTest(controllers = {AuthController.class,
        UserController.class})
@Import(SecurityConfig.class)
class UserControllerTest {
    @Autowired MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean UserService userService;

    @MockitoBean JwtUtil jwtUtil;

    private RegisterRequest validRegisterRequest;
    private LoginRequest validLoginRequest;
    private UserResponse mockResponse;

    @BeforeEach
    void setUp() {
        validRegisterRequest = new RegisterRequest();
        validRegisterRequest.setFirstName("Muso");
        validRegisterRequest.setLastName("Nkuntsu");
        validRegisterRequest.setEmail("muso@traabco.com");
        validRegisterRequest.setPassword("password123");
        validRegisterRequest.setRole(UserRole.ADMIN);

        validLoginRequest = new LoginRequest();
        validLoginRequest.setEmail("muso@traabco.com");
        validLoginRequest.setPassword("password123");

        mockResponse = new UserResponse();
        mockResponse.setId(1L);
        mockResponse.setFirstName("Muso");
        mockResponse.setLastName("Nkuntsu");
        mockResponse.setEmail("muso@traabco.com");
        mockResponse.setRole(UserRole.ADMIN);
    }

    // ── POST /api/auth/register ───────────────────────────────────────────

    @Test
    void register_validRequest_returns201() throws Exception {
        when(userService.registerUser(any())).thenReturn(mockResponse);

        mockMvc.perform(post("/api/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRegisterRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("muso@traabco.com"));
    }

    @Test
    void register_missingFields_returns400() throws Exception {
        RegisterRequest bad = new RegisterRequest();
        bad.setEmail("not-an-email");

        mockMvc.perform(post("/api/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bad)))
                .andExpect(status().isBadRequest());
    }

    // ── POST /api/auth/login ──────────────────────────────────────────────

    @Test
    void login_validCredentials_returns200WithToken() throws Exception {
        when(userService.loginUser(any())).thenReturn("mock.jwt.token");

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validLoginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("mock.jwt.token"));
    }

    @Test
    void login_missingFields_returns400() throws Exception {
        LoginRequest bad = new LoginRequest();

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bad)))
                .andExpect(status().isBadRequest());
    }

    // ── GET /api/users ────────────────────────────────────────────────────

    @Test
    void getAllUsers_noToken_returns403() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers_adminToken_returns200() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(mockResponse));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("muso@traabco.com"));
    }

    // ── GET /api/users/{id} ───────────────────────────────────────────────

    @Test
    @WithMockUser(roles = "ADMIN")
    void getUserById_validId_returns200() throws Exception {
        when(userService.getUserById(1L)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // ── DELETE /api/users/{id} ────────────────────────────────────────────

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_validId_returns204() throws Exception {
        mockMvc.perform(delete("/api/users/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_noToken_returns403() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isForbidden());
    }

}
