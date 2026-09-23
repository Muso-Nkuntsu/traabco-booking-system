package com.cput.traabcobusinessplatform.payment.service;


import com.cput.traabcobusinessplatform.payment.domain.enums.PaymentStatus;
import com.cput.traabcobusinessplatform.payment.dto.PaymentRequest;
import com.cput.traabcobusinessplatform.payment.dto.PaymentResponse;

import java.util.List;

/**Muso Nkuntsu -231223722
 * */
public interface PaymentService {

    PaymentResponse recordPayment(PaymentRequest request);

    List<PaymentResponse> getAllPayments();

    PaymentResponse getPaymentById(Long id);

    List<PaymentResponse> getPaymentsByBooking(Long bookingId);

    List<PaymentResponse> getPaymentsByClient(Long clientId);

    PaymentResponse updatePaymentStatus(Long id, PaymentStatus status);

    void deletePayment(Long id);
}
