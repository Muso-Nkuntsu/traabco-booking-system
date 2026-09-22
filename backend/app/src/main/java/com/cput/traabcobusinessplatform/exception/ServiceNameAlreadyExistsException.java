package com.cput.traabcobusinessplatform.exception;


/**
 * Muso Nkuntsu-231223722
 * Thrown when a service offering with the same name already exists
 * caught by GlobalExcpetion and returned as 409.
 * */
public class ServiceNameAlreadyExistsException extends RuntimeException{
    public ServiceNameAlreadyExistsException(String message){
        super(message);
    }
}
