package com.cput.traabcobusinessplatform.clientTest.mapper;


import com.cput.traabcobusinessplatform.client.dto.ClientRequest;
import com.cput.traabcobusinessplatform.client.dto.ClientResponse;
import com.cput.traabcobusinessplatform.client.domain.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;



class ClientMapperTest {

    private ClientMapper clientMapper;

    @BeforeEach
    void setUp() {
        clientMapper = new ClientMapperImpl();
    }

    @Test
    void toEntity_mapsAllRequestFields_andIgnoresIdAndCreatedAt() {
        ClientRequest request = ClientRequest.builder()
                .email("jane@acme.co.za")
                .taxNumber("TX12345")
                .firstName("Jane")
                .lastName("Doe")
                .companyName("Acme Pty Ltd")
                .address("12 Main Rd, Cape Town")
                .build();

        Client entity = clientMapper.toEntity(request);

        assertThat(entity.getClientid()).isNull();
        assertThat(entity.getCreatedAt()).isNull();
        assertThat(entity.getEmail()).isEqualTo("jane@acme.co.za");
        assertThat(entity.getTaxNumber()).isEqualTo("TX12345");
        assertThat(entity.getFirstName()).isEqualTo("Jane");
        assertThat(entity.getLastName()).isEqualTo("Doe");
        assertThat(entity.getCompanyName()).isEqualTo("Acme Pty Ltd");
        assertThat(entity.getAddress()).isEqualTo("12 Main Rd, Cape Town");
    }

    @Test
    void toEntity_returnsNull_whenRequestIsNull() {
        assertThat(clientMapper.toEntity(null)).isNull();
    }

    @Test
    void toResponse_mapsAllEntityFields() {
        LocalDateTime now = LocalDateTime.now();
        Client entity = Client.builder()
                .clientid(1L)
                .email("jane@acme.co.za")
                .taxNumber("TX12345")
                .firstName("Jane")
                .lastName("Doe")
                .companyName("Acme Pty Ltd")
                .address("12 Main Rd, Cape Town")
                .createdAt(now)
                .build();

        ClientResponse response = clientMapper.toResponse(entity);

        assertThat(response.getid()).isEqualTo(1L);
        assertThat(response.getEmail()).isEqualTo("jane@acme.co.za");
        assertThat(response.getTaxNumber()).isEqualTo("TX12345");
        assertThat(response.getFirstName()).isEqualTo("Jane");
        assertThat(response.getLastName()).isEqualTo("Doe");
        assertThat(response.getCompanyName()).isEqualTo("Acme Pty Ltd");
        assertThat(response.getAddress()).isEqualTo("12 Main Rd, Cape Town");
        assertThat(response.getCreatedAt()).isEqualTo(now);
    }

    @Test
    void toResponse_returnsNull_whenClientIsNull() {
        assertThat(clientMapper.toResponse(null)).isNull();
    }

    @Test
    void updateEntityFromRequest_overwritesFields_butPreservesIdAndCreatedAt() {
        LocalDateTime originalCreatedAt = LocalDateTime.now().minusDays(30);
        Client existing = Client.builder()
                .clientid(1L)
                .email("old@acme.co.za")
                .taxNumber("OLD123")
                .firstName("Old")
                .lastName("Name")
                .companyName("Old Co")
                .address("Old Address")
                .createdAt(originalCreatedAt)
                .build();

        ClientRequest updateRequest = ClientRequest.builder()
                .email("new@acme.co.za")
                .taxNumber("NEW456")
                .firstName("New")
                .lastName("Name")
                .companyName("New Co")
                .address("New Address")
                .build();

        clientMapper.updateEntityFromRequest(updateRequest, existing);


        assertThat(existing.getClientid()).isEqualTo(1L);
        assertThat(existing.getCreatedAt()).isEqualTo(originalCreatedAt);

        assertThat(existing.getEmail()).isEqualTo("new@acme.co.za");
        assertThat(existing.getTaxNumber()).isEqualTo("NEW456");
        assertThat(existing.getFirstName()).isEqualTo("New");
        assertThat(existing.getCompanyName()).isEqualTo("New Co");
        assertThat(existing.getAddress()).isEqualTo("New Address");
    }
}
