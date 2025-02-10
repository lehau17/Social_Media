package com.booking.application.service;

import com.booking.application.model.UserResponse;
import com.booking.domain.model.entity.User;
import com.booking.domain.model.entity.UserCreateDto;

import java.util.Optional;

public interface UserServiceApplication {
    Optional<User> findByUsername(String username);
    UserResponse login(String username, String password) throws Exception;
    UserResponse register(UserCreateDto user) throws Exception;
}
