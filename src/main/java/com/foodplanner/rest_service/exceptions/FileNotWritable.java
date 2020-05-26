package com.foodplanner.rest_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error in writing to property file")
public class FileNotWritable extends IOException {

    public FileNotWritable() {
    }

    public FileNotWritable(String message) {
        super(message);
    }

    public FileNotWritable(String message, Throwable cause) {
        super(message, cause);
    }
}
