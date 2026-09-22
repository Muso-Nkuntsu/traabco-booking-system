package com.cput.traabcobusinessplatform.exception;
/**
 * Muiso Nkuntsu-231223722
 * Thrown when a service offering cannot be founf by ID.
 * Caught by GlobalExceptionHandler and converted to a 404 response.
 */


public class ServiceNotFoundException extends RuntimeException{
    public ServiceNotFoundException(String message){
        super(message);
    }
}

