package com.cput.traabcobusinessplatform.booking.service.impl;


import com.cput.traabcobusinessplatform.Service.domain.ServiceEntity;
import com.cput.traabcobusinessplatform.Service.repository.ServiceOfferingRepository;
import com.cput.traabcobusinessplatform.booking.domain.Booking;
import com.cput.traabcobusinessplatform.booking.dto.BookingRequest;
import com.cput.traabcobusinessplatform.booking.dto.BookingResponse;
import com.cput.traabcobusinessplatform.booking.enums.BookingStatus;
import com.cput.traabcobusinessplatform.booking.mapper.BookingMapper;
import com.cput.traabcobusinessplatform.booking.repository.BookingRepository;
import com.cput.traabcobusinessplatform.booking.service.BookingService;
import com.cput.traabcobusinessplatform.client.domain.Client;
import com.cput.traabcobusinessplatform.client.repository.ClientRepository;
import com.cput.traabcobusinessplatform.exception.ClientNotFoundException;
import com.cput.traabcobusinessplatform.exception.ServiceNotFoundException;
import com.cput.traabcobusinessplatform.exception.UserNotFoundException;
import com.cput.traabcobusinessplatform.exception.BookingNotFoundException;

import com.cput.traabcobusinessplatform.users.domain.UserEntity;
import com.cput.traabcobusinessplatform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**Muso Nkuntsu-231223722*/

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final ClientRepository clientRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;
    private final UserRepository userRepository;

    @Override
    public BookingResponse createBooking(BookingRequest request){
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() ->
                        new ClientNotFoundException("Client not found with ID: "
                        + request.getClientId()));

        ServiceEntity serviceEntity = serviceOfferingRepository.findById(request.getServiceId())
                .orElseThrow(() ->
                        new ServiceNotFoundException("Service not found with ID: "
                        + request.getServiceId()));

        UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new UserNotFoundException("User not found with ID: " + request.getUserId())
        );

        Booking booking = Booking.builder()
                .client(client)
                .service(serviceEntity)
                .user(user)
                .scheduleAt(request.getScheduleAt())
                .notes(request.getNotes())
                .status(BookingStatus.PENDING)
                .build();

        return bookingMapper.toResponse(bookingRepository.save(booking));
    }
    @Override
    public List<BookingResponse> getAllBookings(){
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toResponse)
                .toList();
    }
    @Override
    public BookingResponse getBookingById(Long id){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException (
                        "Booking not found with ID: " + id));

        return bookingMapper.toResponse(booking);
    }

    @Override
    public List<BookingResponse> getBookingsByClient(Long clientId){
        return bookingRepository.findByClient_Clientid(clientId).stream()
                .map(bookingMapper::toResponse)
                .toList();
    }
    @Override
    public List<BookingResponse> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(bookingMapper::toResponse)
                .toList();
    }
    @Override
    public BookingResponse updateBookingStatus(Long id, BookingRequest request){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(
                        "Booking not found with ID: " + id));

        booking.setStatus(request.getStatus());
        Booking updated = bookingRepository.save(booking);

        if (request.getStatus() == BookingStatus.CONFIRMED) {
            // TODO: wire engagementService.createFromBooking() once Engagement module is built

        }
        return bookingMapper.toResponse(updated);
    }
    @Override
    public void deleteBooking(Long id){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(
                        "Booking not found with ID: " + id));
        bookingRepository.delete(booking);
    }

}
