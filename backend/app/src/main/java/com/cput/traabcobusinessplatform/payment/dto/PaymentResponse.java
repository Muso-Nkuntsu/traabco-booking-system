package com.cput.traabcobusinessplatform.payment.dto;


import com.cput.traabcobusinessplatform.payment.domain.enums.PaymentMethod;
import com.cput.traabcobusinessplatform.payment.domain.enums.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

/**Muso Nkuntsu -231223722
 * Outbound DTO — what the API sends back after any payment operation.
 * Returns IDs of related entities rather than full nested objects
 * to avoid circular references.
 * */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Long id;
    private Long bookingId;
    private Long clientId;
    private Double amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private LocalDateTime paidAt;
}
