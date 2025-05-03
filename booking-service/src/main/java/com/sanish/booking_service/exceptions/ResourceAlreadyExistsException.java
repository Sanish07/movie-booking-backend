package com.sanish.booking_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String resourceType, String resourceName, String resourceValue) {
        super(resourceType + " already exists with, " + resourceName + " : " + resourceValue);
    }

}
