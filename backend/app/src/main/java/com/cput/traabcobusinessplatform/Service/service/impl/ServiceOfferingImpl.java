package com.cput.traabcobusinessplatform.Service.service.impl;


import com.cput.traabcobusinessplatform.Service.domain.ServiceEntity;
import com.cput.traabcobusinessplatform.Service.dto.ServiceRequest;
import com.cput.traabcobusinessplatform.Service.dto.ServiceResponse;
import com.cput.traabcobusinessplatform.Service.mapper.ServiceOfferingMapper;
import com.cput.traabcobusinessplatform.Service.repository.ServiceOfferingRepository;
import com.cput.traabcobusinessplatform.Service.service.ServiceOffering;
import com.cput.traabcobusinessplatform.exception.ServiceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**Muso Nkuntsu
 *
 * */

@Service
@RequiredArgsConstructor
public class ServiceOfferingImpl implements ServiceOffering {

    private final ServiceOfferingRepository serviceOfferingRepository;
    private final ServiceOfferingMapper serviceOfferingMapper;

    @Override
    public ServiceResponse createService(ServiceRequest request) {
        if (serviceOfferingRepository.existsByServiceName(request.getServiceName())) {
            throw new IllegalArgumentException("Service with the same name already exists");
        }
             ServiceEntity serviceEntity = serviceOfferingMapper.toEntity(request);
             ServiceEntity saved = serviceOfferingRepository.save(serviceEntity);
            return serviceOfferingMapper.toResponse(saved);
    }

    @Override
    public List<ServiceResponse> getAllServices() {
        return serviceOfferingRepository.findAll()
                .stream()
                .map(serviceOfferingMapper::toResponse)
                .toList();
    }

    @Override
    public ServiceResponse getServiceById(Long id) {
        ServiceEntity serviceEntity = serviceOfferingRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found with id: " + id));
        return serviceOfferingMapper.toResponse(serviceEntity);
    }

    @Override
    public ServiceResponse updateService(Long id, ServiceRequest request) {
        ServiceEntity serviceEntity = serviceOfferingRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found with id: " + id));
        serviceEntity.setServiceName(request.getServiceName());
        serviceEntity.setDescription(request.getDescription());
        serviceEntity.setPrice(request.getPrice());
        serviceEntity.setDefaultDurationMinutes(request.getDefaultDurationMinutes());
        serviceEntity.setCategory(request.getCategory());
        serviceEntity.setIsActive(request.getIsActive());
        ServiceEntity updated = serviceOfferingRepository.save(serviceEntity);
        return serviceOfferingMapper.toResponse(updated);
    }

    @Override
    public void deleteService(Long id) {
        ServiceEntity service = serviceOfferingRepository.findById(id)
                .orElseThrow(()-> new ServiceNotFoundException(
                        "Service not found with id: " + id));
        serviceOfferingRepository.delete(service);

    }
}
