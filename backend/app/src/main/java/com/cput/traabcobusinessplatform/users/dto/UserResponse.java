package com.cput.traabcobusinessplatform.users.dto;


import com.cput.traabcobusinessplatform.users.domain.enums.UserRole;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Muso Nkuntsu
 * Represents the user data the server sends back to the client.
 * This is an outbound DTO — it goes out from the server, never comes in.
 * It deliberately excludes the password field so sensitive credentials
 * are never exposed in an API response. This is what gets returned after
 * a successful register, login, or any user lookup endpoint.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private  Long id;
    private  String firstName;
    private  String lastName;
    private  String email;
    private UserRole role;
    private  LocalDateTime createdAt;


}
