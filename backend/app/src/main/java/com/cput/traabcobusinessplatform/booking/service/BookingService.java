package com.cput.traabcobusinessplatform.booking.service;


import com.cput.traabcobusinessplatform.booking.dto.BookingRequest;
import com.cput.traabcobusinessplatform.booking.dto.BookingResponse;

import java.util.List;

/**Muso Nkiuntsu -231223722 */
public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    List<BookingResponse> getAllBookings();
    BookingResponse getBookingById(Long id);
    List<BookingResponse> getBookingsByClient(Long clientId);
    List<BookingResponse> getBookingsByUser(Long userId);
    BookingResponse updateBookingStatus(Long id, BookingRequest request);
    void deleteBooking(Long id);

}
