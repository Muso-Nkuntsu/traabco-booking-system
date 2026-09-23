package com.cput.traabcobusinessplatform.payment.dto;


import com.cput.traabcobusinessplatform.payment.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**Muso Nkuntsu -231223722
 * */

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequest {

    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;

    @NotNull(message = "Payment method is required")
    private PaymentMethod method;
}
