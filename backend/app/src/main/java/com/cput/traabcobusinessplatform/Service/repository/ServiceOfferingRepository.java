package com.cput.traabcobusinessplatform.Service.repository;


import com.cput.traabcobusinessplatform.Service.domain.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**Muso Nkuntsu-231223722
 *
 * */

@Repository
public interface ServiceOfferingRepository extends JpaRepository<ServiceEntity, Long> {
    boolean existsByServiceName(String serviceName);
}
