package com.cput.traabcobusinessplatform.engagement.controller;


import com.cput.traabcobusinessplatform.engagement.domain.enums.EngagementStatus;
import com.cput.traabcobusinessplatform.engagement.dto.EngagementRequest;
import com.cput.traabcobusinessplatform.engagement.dto.EngagementResponse;
import com.cput.traabcobusinessplatform.engagement.service.EngagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**Muso Nkuntsu -231223722
 * REST controller for managing engagements.
 * Engagements are auto-created when a booking is CONFIRMED.
 * Consultants update status as work progresses.
 * Admins have full control.
 */

@RestController
@RequestMapping("/api/engagements")
@RequiredArgsConstructor
public class EngagementController {
    private final EngagementService engagementService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EngagementResponse> createEngagement(
            @Valid @RequestBody EngagementRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(engagementService.createEngagement(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CONSULTANT')")
    public ResponseEntity<List<EngagementResponse>> getAllEngagements() {
        return ResponseEntity.ok(engagementService.getAllEngagements());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONSULTANT')")
    public ResponseEntity<EngagementResponse> getEngagementById(
            @PathVariable Long id) {
        return ResponseEntity.ok(engagementService.getEngagementById(id));
    }

    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONSULTANT')")
    public ResponseEntity<List<EngagementResponse>> getEngagementsByClient(
            @PathVariable Long clientId) {
        return ResponseEntity.ok(engagementService.getEngagementsByClient(clientId));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONSULTANT')")
    public ResponseEntity<List<EngagementResponse>> getEngagementsByUser(
            @PathVariable Long userId) {
        return ResponseEntity.ok(engagementService.getEngagementsByUser(userId));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONSULTANT')")
    public ResponseEntity<EngagementResponse> updateEngagementStatus(
            @PathVariable Long id,
            @RequestParam EngagementStatus status) {
        return ResponseEntity.ok(
                engagementService.updateEngagementStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEngagement(@PathVariable Long id) {
        engagementService.deleteEngagement(id);
        return ResponseEntity.noContent().build();
    }
}
