package com.cput.traabcobusinessplatform.engagement.service;


import com.cput.traabcobusinessplatform.engagement.domain.enums.EngagementStatus;
import com.cput.traabcobusinessplatform.engagement.dto.EngagementRequest;
import com.cput.traabcobusinessplatform.engagement.dto.EngagementResponse;

import java.util.List;

/**Muso Nkuntsu -231223722
 * Contract for all engagement business operations.
 * The controller only talks to this interface —
 * never directly to the implementation.
 */
public interface EngagementService {
    EngagementResponse createEngagement(EngagementRequest request);

    EngagementResponse createFromBooking(Long bookingId);

    List<EngagementResponse> getAllEngagements();

    EngagementResponse getEngagementById(Long id);

    List<EngagementResponse> getEngagementsByClient(Long clientId);

    List<EngagementResponse> getEngagementsByUser(Long userId);

    EngagementResponse updateEngagementStatus(Long id, EngagementStatus status);

    void deleteEngagement(Long id);
}
