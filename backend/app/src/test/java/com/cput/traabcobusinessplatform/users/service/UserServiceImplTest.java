package com.cput.traabcobusinessplatform.users.service;


import com.cput.traabcobusinessplatform.config.JwtUtil;
import com.cput.traabcobusinessplatform.exception.UserNotFoundException;
import com.cput.traabcobusinessplatform.users.domain.UserEntity;
import com.cput.traabcobusinessplatform.users.domain.enums.UserRole;
import com.cput.traabcobusinessplatform.users.dto.LoginRequest;
import com.cput.traabcobusinessplatform.users.dto.RegisterRequest;
import com.cput.traabcobusinessplatform.users.dto.UserResponse;
import com.cput.traabcobusinessplatform.users.mapper.UserMapper;
import com.cput.traabcobusinessplatform.users.repository.UserRepository;
import com.cput.traabcobusinessplatform.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Muso Nkuntsu
 * */

@ExtendWith(MockitoExtension.class)
 class UserServiceImplTest {
    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity mockUser;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private UserResponse mockResponse;


    @BeforeEach
    void setUp(){
        mockUser = UserEntity.builder()
                .id(1L)
                .firstName("Muso")
                .lastName("Nkntsu")
                .email("musotukwayo@traabco.com")
                .password("hashedPassword")
                .role(UserRole.ADMIN)
                .build();


        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Muso");
        registerRequest.setLastName("Nkntsu");
        registerRequest.setEmail("musotukwayo@traabco.com");
        registerRequest.setPassword("password123");
        registerRequest.setRole(UserRole.ADMIN);


        loginRequest = new LoginRequest();
        loginRequest.setEmail("musotukwayo@traabco.com");
        loginRequest.setPassword("password123");


        mockResponse = new UserResponse();
        mockResponse.setId(1L);
        mockResponse.setFirstName("Muso Nkuntsu");
        mockResponse.setLastName("Nkuntsu");
        mockResponse.setEmail("musotukwayo@traabco.com");
        mockResponse.setRole(UserRole.ADMIN);
    }

    // ------register-----------------------

    @Test
    void registerUser_happyPath_returnUserResponse(){
        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);
        when(userMapper.toEntity(registerRequest)).thenReturn(mockUser);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        when(userMapper.toResponse(mockUser)).thenReturn(mockResponse);

        UserResponse result = userService.registerUser(registerRequest);
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("musotukwayo@traabco.com");
        verify(userRepository).save(mockUser);
    }
    @Test
    void RegisterUser_duplicateEmail_throwsException(){
        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> userService.registerUser(registerRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Email already exists");

        verify(userRepository, never()).save(any());
    }

    //----LoginUser----------
    @Test
    void loginUser_happyPath_returnToken(){
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(loginRequest.getPassword(), mockUser.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(mockUser.getEmail(), mockUser.getRole().name()))
                .thenReturn("mock.jwt.token");

        String token = userService.loginUser(loginRequest);
        assertThat(token).isEqualTo("mock.jwt.token");
    }
    @Test
    void loginUser_wrongEmail_throwsUserNotFoundException() {
        when(userRepository.findByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.loginUser(loginRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("No account found");
    }

    @Test
    void loginUser_wrongPassword_throwsUserNotFoundException() {
        when(userRepository.findByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(loginRequest.getPassword(), mockUser.getPassword()))
                .thenReturn(false);

        assertThatThrownBy(() -> userService.loginUser(loginRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("Invalid credentials");
    }

    // ── getAllUsers ───────────────────────────────────────────────────────

    @Test
    void getAllUsers_returnsMappedList() {
        when(userRepository.findAll()).thenReturn(List.of(mockUser));
        when(userMapper.toResponse(mockUser)).thenReturn(mockResponse);

        List<UserResponse> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo("musotukwayo@traabco.com");
    }

    // ── getUserById ───────────────────────────────────────────────────────

    @Test
    void getUserById_found_returnsUserResponse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userMapper.toResponse(mockUser)).thenReturn(mockResponse);

        UserResponse result = userService.getUserById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getUserById_notFound_throwsUserNotFoundException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(99L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with id: 99");
    }

    // ── updateUser ────────────────────────────────────────────────────────

    @Test
    void updateUser_happyPath_returnsUpdatedResponse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        when(userMapper.toResponse(mockUser)).thenReturn(mockResponse);

        UserResponse result = userService.updateUser(1L, registerRequest);

        assertThat(result).isNotNull();
        verify(userRepository).save(mockUser);
    }

    @Test
    void updateUser_notFound_throwsUserNotFoundException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUser(99L, registerRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with id: 99");
    }

    // ── deleteUser ────────────────────────────────────────────────────────

    @Test
    void deleteUser_happyPath_deletesSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        userService.deleteUser(1L);

        verify(userRepository).delete(mockUser);
    }

    @Test
    void deleteUser_notFound_throwsUserNotFoundException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.deleteUser(99L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with id: 99");
    }

}
