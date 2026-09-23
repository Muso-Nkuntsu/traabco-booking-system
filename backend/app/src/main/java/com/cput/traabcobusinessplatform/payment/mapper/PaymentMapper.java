package com.cput.traabcobusinessplatform.payment.mapper;


import com.cput.traabcobusinessplatform.payment.domain.Payment;
import com.cput.traabcobusinessplatform.payment.dto.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**Muso Nkiuntsu -231223722
 * */

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "clientId", source = "client.clientid")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "method", source = "method")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "paidAt", source = "paidAt")
    PaymentResponse toResponse(Payment payment);
}
