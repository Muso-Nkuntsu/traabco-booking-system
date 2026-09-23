package com.cput.traabcobusinessplatform.engagement.repository;


import com.cput.traabcobusinessplatform.engagement.domain.Engagement;
import com.cput.traabcobusinessplatform.engagement.domain.enums.EngagementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** Muso Nkuntsu -231223722
 * Repository for Engagement entity.
 * Custom queries allow filtering by client,
 * consultant, status, and booking.
 */
@Repository
public interface EngagementRepository extends JpaRepository<Engagement, Long> {
    List<Engagement> findByClientClientid(Long clientId);

    List<Engagement> findByUserId(Long userId);

    List<Engagement> findByStatus(EngagementStatus status);

    Optional<Engagement> findByBookingId(Long bookingId);
}
