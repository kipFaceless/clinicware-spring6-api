package com.clinicware.clinicware.models;

import org.springframework.stereotype.Component;

@Component
public class FeedBackMessage {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
