package com.cput.traabcobusinessplatform.clientTest.controller;


import com.cput.traabcobusinessplatform.client.controller.ClientController;
import com.cput.traabcobusinessplatform.client.dto.ClientRequest;
import com.cput.traabcobusinessplatform.client.dto.ClientResponse;
import com.cput.traabcobusinessplatform.client.service.ClientService;
import com.cput.traabcobusinessplatform.config.JwtUtil;
import com.cput.traabcobusinessplatform.config.SecurityConfig;
import org.springframework.context.annotation.Import;
import com.cput.traabcobusinessplatform.exception.ClientEmailAlreadyExistsException;
import com.cput.traabcobusinessplatform.exception.ClientNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ClientService clientService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private com.cput.traabcobusinessplatform.config.JwtAuthFilter jwtAuthFilter;

    private ClientRequest validRequest() {
        return ClientRequest.builder()
                .email("jane@acme.co.za")
                .taxNumber("TX12345")
                .firstName("Jane")
                .lastName("Doe")
                .companyName("Acme Pty Ltd")
                .address("12 Main Rd, Cape Town")
                .build();
    }

    private ClientResponse sampleResponse() {
        return ClientResponse.builder()
                .id(1L)
                .email("jane@acme.co.za")
                .taxNumber("TX12345")
                .firstName("Jane")
                .lastName("Doe")
                .companyName("Acme Pty Ltd")
                .address("12 Main Rd, Cape Town")
                .createdAt(LocalDateTime.now())
                .build();
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void create_returns201AndClient_whenAdminAndValidRequest() throws Exception {
        when(clientService.create(any(ClientRequest.class))).thenReturn(sampleResponse());

        mockMvc.perform(post("/api/clients")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.email").value("jane@acme.co.za"));
    }

    @Test
    @WithMockUser(roles = "CONSULTANT")
    void create_returns403_whenCallerIsNotAdmin() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isForbidden());

        verify(clientService, never()).create(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_returns400_whenFieldsAreBlank() throws Exception {
        ClientRequest blank = ClientRequest.builder()
                .email("")
                .taxNumber("")
                .firstName("")
                .lastName("")
                .companyName("")
                .address("")
                .build();

        mockMvc.perform(post("/api/clients")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blank)))
                .andExpect(status().isBadRequest());

        verify(clientService, never()).create(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_returns400_whenEmailIsInvalid() throws Exception {
        ClientRequest invalidEmail = validRequest();
        invalidEmail.setEmail("not-an-email");

        mockMvc.perform(post("/api/clients")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmail)))
                .andExpect(status().isBadRequest());

        verify(clientService, never()).create(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_returns409_whenEmailAlreadyExists() throws Exception {
        when(clientService.create(any(ClientRequest.class)))
                .thenThrow(new ClientEmailAlreadyExistsException("A client with email 'jane@acme.co.za' already exists"));

        mockMvc.perform(post("/api/clients")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isConflict());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void getAll_returns200_whenAdmin() throws Exception {
        when(clientService.getAll()).thenReturn(List.of(sampleResponse()));

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)));
    }

    @Test
    @WithMockUser(roles = "CONSULTANT")
    void getAll_returns200_whenConsultant() throws Exception {
        when(clientService.getAll()).thenReturn(List.of(sampleResponse()));

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk());
    }

    @Test
    void getAll_returns401_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void getById_returns200_whenClientExists() throws Exception {
        when(clientService.getById(1L)).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/clients/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getById_returns404_whenClientDoesNotExist() throws Exception {
        when(clientService.getById(99L))
                .thenThrow(new ClientNotFoundException("Client not found with id: 99"));

        mockMvc.perform(get("/api/clients/{id}", 99L))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "CONSULTANT")
    void search_byName_returns200() throws Exception {
        when(clientService.search("Jane", null)).thenReturn(List.of(sampleResponse()));

        mockMvc.perform(get("/api/clients/search").param("name", "Jane"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].firstName").value("Jane"));
    }

    @Test
    @WithMockUser(roles = "CONSULTANT")
    void search_byTaxNumber_returns200() throws Exception {
        when(clientService.search(null, "TX12345")).thenReturn(List.of(sampleResponse()));

        mockMvc.perform(get("/api/clients/search").param("taxNumber", "TX12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].taxNumber").value("TX12345"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void update_returns200_whenAdminAndValid() throws Exception {
        when(clientService.update(eq(1L), any(ClientRequest.class))).thenReturn(sampleResponse());

        mockMvc.perform(put("/api/clients/{id}", 1L)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    @WithMockUser(roles = "CONSULTANT")
    void update_returns403_whenCallerIsNotAdmin() throws Exception {
        mockMvc.perform(put("/api/clients/{id}", 1L)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isForbidden());

        verify(clientService, never()).update(anyLong(), any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update_returns404_whenClientDoesNotExist() throws Exception {
        when(clientService.update(eq(99L), any(ClientRequest.class)))
                .thenThrow(new ClientNotFoundException("Client not found with id: 99"));

        mockMvc.perform(put("/api/clients/{id}", 99L)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_returns200_whenAdmin() throws Exception {
        doNothing().when(clientService).delete(1L);

        mockMvc.perform(delete("/api/clients/{id}", 1L).with(csrf()))
                .andExpect(status().isOk());

        verify(clientService).delete(1L);
    }

    @Test
    @WithMockUser(roles = "CONSULTANT")
    void delete_returns403_whenCallerIsNotAdmin() throws Exception {
        mockMvc.perform(delete("/api/clients/{id}", 1L).with(csrf()))
                .andExpect(status().isForbidden());

        verify(clientService, never()).delete(anyLong());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_returns404_whenClientDoesNotExist() throws Exception {
        doThrow(new ClientNotFoundException("Client not found with id: 99"))
                .when(clientService).delete(99L);

        mockMvc.perform(delete("/api/clients/{id}", 99L).with(csrf()))
                .andExpect(status().isNotFound());
    }
}
