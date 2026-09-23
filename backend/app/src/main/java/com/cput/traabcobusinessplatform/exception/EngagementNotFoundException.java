package com.cput.traabcobusinessplatform.exception;

import com.cput.traabcobusinessplatform.engagement.dto.EngagementRequest;

public class EngagementNotFoundException  extends RuntimeException{

    public EngagementNotFoundException(String message){
        super(message);
    }
}
