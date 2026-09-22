package com.cput.traabcobusinessplatform.client.controller;

import com.cput.traabcobusinessplatform.client.dto.ClientRequest;
import com.cput.traabcobusinessplatform.client.dto.ClientResponse;
import com.cput.traabcobusinessplatform.client.service.ClientService;
import com.cput.traabcobusinessplatform.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ClientResponse>> create(@Valid @RequestBody ClientRequest request) {
        ClientResponse response = clientService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Client created successfully"));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CONSULTANT')")
    public ResponseEntity<ApiResponse<List<ClientResponse>>> getAll() {
        List<ClientResponse> clients = clientService.getAll();
        return ResponseEntity.ok(ApiResponse.success(clients, "Clients retrieved successfully"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULTANT')")
    public ResponseEntity<ApiResponse<ClientResponse>> getById(@PathVariable Long id) {
        ClientResponse response = clientService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Client retrieved successfully"));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','CONSULTANT')")
    public ResponseEntity<ApiResponse<List<ClientResponse>>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String taxNumber) {
        List<ClientResponse> results = clientService.search(name, taxNumber);
        return ResponseEntity.ok(ApiResponse.success(results, "Search completed successfully"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ClientResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody ClientRequest request) {
        ClientResponse response = clientService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Client updated successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Client deleted successfully"));
    }
}
