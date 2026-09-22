package com.cput.traabcobusinessplatform.Service.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**Muso Nkuntsu
 * */

@Getter
@Setter
@NoArgsConstructor
public class ServiceRequest {
    @NotBlank(message = "Service name is required")
    private String serviceName;

    @NotBlank(message = "Description is required")
    private String description;


    @NotBlank(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    private Integer defaultDurationMinutes;

    private String category;

    private Boolean isActive = true;
}
