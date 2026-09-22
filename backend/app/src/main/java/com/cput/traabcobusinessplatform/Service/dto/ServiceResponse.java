package com.cput.traabcobusinessplatform.Service.dto;


import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
/**Muso Nkuntsu- 2312237222
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResponse {
    private Long id;
    private String serviceName;
    private String description;
    private Double price;
    private Integer defaultDurationMinutes;
    private String category;

    @Builder .Default
    private Boolean isActive = true;


    private LocalDateTime createdAt;
}
