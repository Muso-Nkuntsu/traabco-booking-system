package com.cput.traabcobusinessplatform.clientTest.service;

import com.cput.traabcobusinessplatform.client.dto.ClientRequest;
import com.cput.traabcobusinessplatform.client.dto.ClientResponse;
import com.cput.traabcobusinessplatform.client.domain.Client;
import com.cput.traabcobusinessplatform.client.mapper.ClientMapper;
import com.cput.traabcobusinessplatform.client.repository.ClientRepository;
import com.cput.traabcobusinessplatform.client.service.impl.ClientServiceImpl;
import com.cput.traabcobusinessplatform.exception.ClientEmailAlreadyExistsException;
import com.cput.traabcobusinessplatform.exception.ClientNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;
    private ClientRequest request;
    private ClientResponse response;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .clientid(1L)
                .email("jane@acme.co.za")
                .taxNumber("TX12345")
                .firstName("Jane")
                .lastName("Doe")
                .companyName("Acme Pty Ltd")
                .address("12 Main Rd, Cape Town")
                .createdAt(LocalDateTime.now())
                .build();

        request = ClientRequest.builder()
                .email("jane@acme.co.za")
                .taxNumber("TX12345")
                .firstName("Jane")
                .lastName("Doe")
                .companyName("Acme Pty Ltd")
                .address("12 Main Rd, Cape Town")
                .build();

        response = ClientResponse.builder()
                .id(1L)
                .email("jane@acme.co.za")
                .taxNumber("TX12345")
                .firstName("Jane")
                .lastName("Doe")
                .companyName("Acme Pty Ltd")
                .address("12 Main Rd, Cape Town")
                .createdAt(client.getCreatedAt())
                .build();
    }



    @Test
    void create_savesAndReturnsClient_whenEmailIsUnique() {
        when(clientRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(clientMapper.toEntity(request)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);
        when(clientMapper.toResponse(client)).thenReturn(response);

        ClientResponse result = clientService.create(request);

        assertThat(result).isEqualTo(response);
        verify(clientRepository).existsByEmail("jane@acme.co.za");
        verify(clientRepository).save(client);
    }

    @Test
    void create_throwsClientEmailAlreadyExistsException_whenEmailIsTaken() {
        when(clientRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> clientService.create(request))
                .isInstanceOf(ClientEmailAlreadyExistsException.class)
                .hasMessageContaining(request.getEmail());

        verify(clientRepository, never()).save(any());
    }



    @Test
    void getAll_returnsMappedListOfAllClients() {
        when(clientRepository.findAll()).thenReturn(List.of(client));
        when(clientMapper.toResponse(client)).thenReturn(response);

        List<ClientResponse> results = clientService.getAll();

        assertThat(results).hasSize(1).containsExactly(response);
    }

    @Test
    void getAll_returnsEmptyList_whenNoClientsExist() {
        when(clientRepository.findAll()).thenReturn(List.of());

        List<ClientResponse> results = clientService.getAll();

        assertThat(results).isEmpty();
    }


    @Test
    void getById_returnsClient_whenIdExists() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientMapper.toResponse(client)).thenReturn(response);

        ClientResponse result = clientService.getById(1L);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void getById_throwsClientNotFoundException_whenIdDoesNotExist() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.getById(99L))
                .isInstanceOf(ClientNotFoundException.class)
                .hasMessageContaining("99");
    }


    @Test
    void search_byName_delegatesToFindByFirstName() {
        when(clientRepository.findByFirstNameContainingIgnoreCase("Jane"))
                .thenReturn(List.of(client));
        when(clientMapper.toResponse(client)).thenReturn(response);

        List<ClientResponse> results = clientService.search("Jane", null);

        assertThat(results).containsExactly(response);
        verify(clientRepository).findByFirstNameContainingIgnoreCase("Jane");
        verify(clientRepository, never()).findByTaxNumberContainingIgnoreCase(anyString());
    }

    @Test
    void search_byTaxNumber_delegatesToFindByTaxNumber_whenNameIsBlank() {
        when(clientRepository.findByTaxNumberContainingIgnoreCase("TX12345"))
                .thenReturn(List.of(client));
        when(clientMapper.toResponse(client)).thenReturn(response);

        List<ClientResponse> results = clientService.search(null, "TX12345");

        assertThat(results).containsExactly(response);
        verify(clientRepository).findByTaxNumberContainingIgnoreCase("TX12345");
        verify(clientRepository, never()).findByFirstNameContainingIgnoreCase(anyString());
    }

    @Test
    void search_returnsAllClients_whenNoParamsProvided() {
        when(clientRepository.findAll()).thenReturn(List.of(client));
        when(clientMapper.toResponse(client)).thenReturn(response);

        List<ClientResponse> results = clientService.search(null, null);

        assertThat(results).containsExactly(response);
        verify(clientRepository).findAll();
    }


    @Test
    void update_updatesAndReturnsClient_whenIdExistsAndEmailUnchanged() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);
        when(clientMapper.toResponse(client)).thenReturn(response);

        ClientResponse result = clientService.update(1L, request);

        assertThat(result).isEqualTo(response);
        verify(clientMapper).updateEntityFromRequest(request, client);
        verify(clientRepository, never()).existsByEmail(anyString());
    }

    @Test
    void update_throwsClientNotFoundException_whenIdDoesNotExist() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.update(99L, request))
                .isInstanceOf(ClientNotFoundException.class);

        verify(clientRepository, never()).save(any());
    }

    @Test
    void update_throwsClientEmailAlreadyExistsException_whenNewEmailTakenByAnotherClient() {
        ClientRequest changedEmailRequest = ClientRequest.builder()
                .email("new@acme.co.za")
                .taxNumber("TX12345")
                .firstName("Jane")
                .lastName("Doe")
                .companyName("Acme Pty Ltd")
                .address("12 Main Rd, Cape Town")
                .build();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.existsByEmail("new@acme.co.za")).thenReturn(true);

        assertThatThrownBy(() -> clientService.update(1L, changedEmailRequest))
                .isInstanceOf(ClientEmailAlreadyExistsException.class);

        verify(clientRepository, never()).save(any());
    }



    @Test
    void delete_removesClient_whenIdExists() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        clientService.delete(1L);

        verify(clientRepository).delete(client);
    }

    @Test
    void delete_throwsClientNotFoundException_whenIdDoesNotExist() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.delete(99L))
                .isInstanceOf(ClientNotFoundException.class);

        verify(clientRepository, never()).delete(any(Client.class));
    }
}
