package com.booking.application.model;

import lombok.Data;




@Data
public class UserLoginResponse {

    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String avatarUrl;
    private String bio;
    private Boolean isActive;
    private Boolean isVerified;
    private Boolean is2faEnabled;
    private String twoFaMethod;
    // token
    private String accessToken;
    private String refreshToken;
}
