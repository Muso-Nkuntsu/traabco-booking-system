package com.cput.traabcobusinessplatform.engagement.service.impl;


import com.cput.traabcobusinessplatform.Service.domain.ServiceEntity;
import com.cput.traabcobusinessplatform.Service.repository.ServiceOfferingRepository;
import com.cput.traabcobusinessplatform.booking.domain.Booking;
import com.cput.traabcobusinessplatform.booking.repository.BookingRepository;
import com.cput.traabcobusinessplatform.client.domain.Client;
import com.cput.traabcobusinessplatform.client.repository.ClientRepository;
import com.cput.traabcobusinessplatform.engagement.domain.Engagement;
import com.cput.traabcobusinessplatform.engagement.domain.enums.EngagementStatus;
import com.cput.traabcobusinessplatform.engagement.dto.EngagementRequest;
import com.cput.traabcobusinessplatform.engagement.dto.EngagementResponse;
import com.cput.traabcobusinessplatform.engagement.mapper.EngagementMapper;
import com.cput.traabcobusinessplatform.engagement.repository.EngagementRepository;
import com.cput.traabcobusinessplatform.engagement.service.EngagementService;
import com.cput.traabcobusinessplatform.exception.*;
import com.cput.traabcobusinessplatform.users.domain.UserEntity;
import com.cput.traabcobusinessplatform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/** Muso Nkuntsu -231223722
 * Implementation of EngagementService.
 * Handles manual engagement creation and auto-creation
 * from a confirmed booking. Consultants update the status
 * as work progresses until COMPLETED triggers payment.
 */


@Service
@RequiredArgsConstructor
public class EngagementServiceImpl implements EngagementService {
    private final EngagementRepository engagementRepository;
    private final EngagementMapper engagementMapper;
    private final ClientRepository clientRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    @Override
    public EngagementResponse createEngagement(EngagementRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(
                        "Client not found with id: " + request.getClientId()));

        ServiceEntity service = serviceOfferingRepository.findById(request.getServiceId())
                .orElseThrow(() -> new ServiceNotFoundException(
                        "Service not found with id: " + request.getServiceId()));

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + request.getUserId()));

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new BookingNotFoundException(
                        "Booking not found with id: " + request.getBookingId()));

        Engagement engagement = Engagement.builder()
                .client(client)
                .service(service)
                .user(user)
                .booking(booking)
                .startDate(request.getStartDate() != null
                        ? request.getStartDate() : LocalDate.now())
                .endDate(request.getEndDate())
                .status(EngagementStatus.ACTIVE)
                .build();

        return engagementMapper.toResponse(engagementRepository.save(engagement));
    }

    @Override
    public EngagementResponse createFromBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(
                        "Booking not found with id: " + bookingId));

        Engagement engagement = Engagement.builder()
                .client(booking.getClient())
                .service(booking.getService())
                .user(booking.getUser())
                .booking(booking)
                .startDate(LocalDate.now())
                .status(EngagementStatus.ACTIVE)
                .build();

        return engagementMapper.toResponse(engagementRepository.save(engagement));
    }

    @Override
    public List<EngagementResponse> getAllEngagements() {
        return engagementRepository.findAll()
                .stream()
                .map(engagementMapper::toResponse)
                .toList();
    }

    @Override
    public EngagementResponse getEngagementById(Long id) {
        Engagement engagement = engagementRepository.findById(id)
                .orElseThrow(() -> new EngagementNotFoundException(
                        "Engagement not found with id: " + id));
        return engagementMapper.toResponse(engagement);
    }

    @Override
    public List<EngagementResponse> getEngagementsByClient(Long clientId) {
        return engagementRepository.findByClientClientid(clientId)
                .stream()
                .map(engagementMapper::toResponse)
                .toList();
    }

    @Override
    public List<EngagementResponse> getEngagementsByUser(Long userId) {
        return engagementRepository.findByUserId(userId)
                .stream()
                .map(engagementMapper::toResponse)
                .toList();
    }

    @Override
    public EngagementResponse updateEngagementStatus(Long id, EngagementStatus status) {
        Engagement engagement = engagementRepository.findById(id)
                .orElseThrow(() -> new EngagementNotFoundException(
                        "Engagement not found with id: " + id));
        engagement.setStatus(status);
        return engagementMapper.toResponse(engagementRepository.save(engagement));
    }

    @Override
    public void deleteEngagement(Long id) {
        Engagement engagement = engagementRepository.findById(id)
                .orElseThrow(() -> new EngagementNotFoundException(
                        "Engagement not found with id: " + id));
        engagementRepository.delete(engagement);
    }

}
