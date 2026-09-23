package com.cput.traabcobusinessplatform.booking.controller;


import com.cput.traabcobusinessplatform.booking.dto.BookingRequest;
import com.cput.traabcobusinessplatform.booking.dto.BookingResponse;
import com.cput.traabcobusinessplatform.booking.dto.BookingStatusUpdateRequest;
import com.cput.traabcobusinessplatform.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**Muso Nuntsu -231223722*/
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor

public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CONSULTANT')")
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookingService.createBooking(request));
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CONSULTANT')")
    public ResponseEntity<BookingResponse> getAllBookings(Long id){
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONSULTANT')")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONSULTANT')")
    public ResponseEntity<List<BookingResponse>> getBookingsByClient(
            @PathVariable Long clientId) {
        return ResponseEntity.ok(bookingService.getBookingsByClient(clientId));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONSULTANT')")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(
            @PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingResponse> updateBookingStatus(
            @PathVariable Long id,
            @Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
