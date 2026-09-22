package com.cput.traabcobusinessplatform.users.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the data a returning user submits when logging in.
 * This is an inbound DTO — it comes from the client, never from the server.
 * Only two fields are needed: email to identify the user and password to
 * verify them. If both match a record in the database, a JWT token is
 * returned. Nothing more is required or accepted.
 */

@Getter
@Setter
@NoArgsConstructor

public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private  String password;
}
