package com.cput.traabcobusinessplatform.client.service;

import com.cput.traabcobusinessplatform.client.dto.ClientRequest;
import com.cput.traabcobusinessplatform.client.dto.ClientResponse;

import java.util.List;

public interface ClientService {

    ClientResponse create(ClientRequest request);

    List<ClientResponse> getAll();

    ClientResponse getById(Long id);

    List<ClientResponse> search(String name, String taxNumber);

    ClientResponse update(Long id, ClientRequest request);

    void delete(Long id);
}
