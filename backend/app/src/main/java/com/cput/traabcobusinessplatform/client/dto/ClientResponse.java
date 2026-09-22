package com.cput.traabcobusinessplatform.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {

    private Long id;
    private String email;
    private String taxNumber;
    private String firstName;
    private String lastName;
    private String companyName;
    private String address;
    private LocalDateTime createdAt;

}
