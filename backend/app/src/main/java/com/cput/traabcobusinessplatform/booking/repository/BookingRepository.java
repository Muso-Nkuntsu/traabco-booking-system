package com.cput.traabcobusinessplatform.booking.repository;

import com.cput.traabcobusinessplatform.booking.domain.Booking;
import com.cput.traabcobusinessplatform.booking.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**Muso Nkuntsu -231223722
 * */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByClientId(Long clientId);

    List<Booking> findByUserId(Long userId);

    List<Booking> findByStatus(BookingStatus status);

}
