package com.cput.traabcobusinessplatform.Service.controller;

import com.cput.traabcobusinessplatform.Service.dto.ServiceRequest;
import com.cput.traabcobusinessplatform.Service.dto.ServiceResponse;
import com.cput.traabcobusinessplatform.Service.service.ServiceOffering;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*
* REST controller for managing TRAABCO'S service catalogue
* Admins manage service - create, update, delete.
* consultants and Admins can view services
* All endpoints require a valid JWT token
* */


@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceOffering serviceOffering;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createService(
            // Implement the logic to create a service
            @Valid @RequestBody ServiceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceOffering.createService(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CONSULTANT')")
    public ResponseEntity<List<ServiceResponse>> getAllServices() {
        return ResponseEntity.ok(serviceOffering.getAllServices());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceResponse> updateService(
            @PathVariable Long id,
            @Valid @RequestBody ServiceRequest serviceRequest) {
        return ResponseEntity.ok(serviceOffering.updateService(id, serviceRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceOffering.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}