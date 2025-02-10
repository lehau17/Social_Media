package com.booking.infrastructure.model.enums;

public enum JwtType {

    ACCESS_TOKEN("access_token"),
    REFRESH_TOKEN("refresh_token");

    private final String value;

    // Constructor
    JwtType(String value) {
        this.value = value;
    }

    // Getter method
    public String getValue() {
        return value;
    }
}
