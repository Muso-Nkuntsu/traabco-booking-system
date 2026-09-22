package com.cput.traabcobusinessplatform.Service.service;

import com.cput.traabcobusinessplatform.Service.dto.ServiceRequest;
import com.cput.traabcobusinessplatform.Service.dto.ServiceResponse;

import java.util.List;

/**Muso Nkuntsu-231223722
 *
 * */


public interface ServiceOffering {
    ServiceResponse createService(ServiceRequest request);

    List<ServiceResponse> getAllServices();

    ServiceResponse getServiceById(Long id);

    ServiceResponse updateService(Long id, ServiceRequest request);

    void deleteService(Long id);
}
