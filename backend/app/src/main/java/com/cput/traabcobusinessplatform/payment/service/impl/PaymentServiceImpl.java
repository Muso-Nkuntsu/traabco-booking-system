package com.cput.traabcobusinessplatform.payment.service.impl;


import com.cput.traabcobusinessplatform.booking.domain.Booking;
import com.cput.traabcobusinessplatform.booking.repository.BookingRepository;
import com.cput.traabcobusinessplatform.client.domain.Client;
import com.cput.traabcobusinessplatform.client.repository.ClientRepository;
import com.cput.traabcobusinessplatform.exception.BookingNotFoundException;
import com.cput.traabcobusinessplatform.exception.ClientNotFoundException;
import com.cput.traabcobusinessplatform.exception.PaymentNotFoundExcpetion;
import com.cput.traabcobusinessplatform.payment.domain.Payment;
import com.cput.traabcobusinessplatform.payment.domain.enums.PaymentStatus;
import com.cput.traabcobusinessplatform.payment.dto.PaymentRequest;
import com.cput.traabcobusinessplatform.payment.dto.PaymentResponse;
import com.cput.traabcobusinessplatform.payment.mapper.PaymentMapper;
import com.cput.traabcobusinessplatform.payment.repository.PaymentRepository;
import com.cput.traabcobusinessplatform.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**Muso Nkuntsu -231223722
 * */

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService

    // Implement the methods from PaymentService interface here
{
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;

    @Override
    public PaymentResponse recordPayment(PaymentRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new BookingNotFoundException(
                        "Booking not found with id: " + request.getBookingId()));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(
                        "Client not found with id: " + request.getClientId()));

        Payment payment = Payment.builder()
                .booking(booking)
                .client(client)
                .amount(request.getAmount())
                .method(request.getMethod())
                .status(PaymentStatus.PENDING)
                .build();

        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundExcpetion(
                        "Payment not found with id: " + id));
        return paymentMapper.toResponse(payment);
    }

    @Override
    public List<PaymentResponse> getPaymentsByBooking(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    public List<PaymentResponse> getPaymentsByClient(Long clientId) {
        return paymentRepository.findByClient_Clientid(clientId)
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    public PaymentResponse updatePaymentStatus(Long id, PaymentStatus status) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundExcpetion(
                        "Payment not found with id: " + id));
        payment.setStatus(status);
        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundExcpetion(
                        "Payment not found with id: " + id));
        paymentRepository.delete(payment);
    }

}
