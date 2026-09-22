package com.cput.traabcobusinessplatform.client.service.impl;

import com.cput.traabcobusinessplatform.client.dto.ClientRequest;
import com.cput.traabcobusinessplatform.client.dto.ClientResponse;
import com.cput.traabcobusinessplatform.client.domain.Client;
import com.cput.traabcobusinessplatform.client.mapper.ClientMapper;
import com.cput.traabcobusinessplatform.client.repository.ClientRepository;
import com.cput.traabcobusinessplatform.client.service.ClientService;
import com.cput.traabcobusinessplatform.exception.ClientEmailAlreadyExistsException;
import com.cput.traabcobusinessplatform.exception.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientResponse create(ClientRequest request) {
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new ClientEmailAlreadyExistsException(
                    "A client with email '" + request.getEmail() + "' already exists");
        }
        Client client = clientMapper.toEntity(request);
        Client saved = clientRepository.save(client);
        return clientMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> getAll() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse getById(Long id) {
        Client client = findClientOrThrow(id);
        return clientMapper.toResponse(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> search(String name, String taxNumber) {
        List<Client> results;

        if (name != null && !name.isBlank()) {
            results = clientRepository.findByFirstNameContainingIgnoreCase(name);
        } else if (taxNumber != null && !taxNumber.isBlank()) {
            results = clientRepository.findByTaxNumberContainingIgnoreCase(taxNumber);
        } else {
            results = clientRepository.findAll();
        }

        return results.stream()
                .map(clientMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClientResponse update(Long id, ClientRequest request) {
        Client client = findClientOrThrow(id);

        // If the email is changing, make sure the new one isn't already taken
        if (!client.getEmail().equalsIgnoreCase(request.getEmail())
                && clientRepository.existsByEmail(request.getEmail())) {
            throw new ClientEmailAlreadyExistsException(
                    "A client with email '" + request.getEmail() + "' already exists");
        }

        clientMapper.updateEntityFromRequest(request, client);
        Client updated = clientRepository.save(client);
        return clientMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        Client client = findClientOrThrow(id);
        clientRepository.delete(client);
    }

    private Client findClientOrThrow(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));
    }
}
