package com.cput.traabcobusinessplatform.payment.repository;

import com.cput.traabcobusinessplatform.payment.domain.Payment;
import com.cput.traabcobusinessplatform.payment.domain.enums.PaymentStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**Muso Nkuntsu -231223722
 * */

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByBookingId(Long bookingId);

    List<Payment> findByClient_Clientid(Long clientid);

    List<Payment> findByStatus(PaymentStatus status);
}
