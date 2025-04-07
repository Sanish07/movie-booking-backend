package com.sanish.movie_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resourceType, String resourceName, String resourceValue) {
        super(resourceType + " not found with, " + resourceName + " : " + resourceValue);
    }

}
