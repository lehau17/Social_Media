package com.booking.domain.service;

import com.booking.domain.model.entity.User;
import com.booking.domain.model.entity.UserCreateDto;

import java.util.Optional;

public interface UserServiceDomain {
    Optional<User> findByUsername(String username);
    Optional<User> login(String username, String password) throws Exception;
    User save(UserCreateDto user) throws Exception;
}
