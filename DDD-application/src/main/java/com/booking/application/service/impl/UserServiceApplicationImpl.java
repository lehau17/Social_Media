package com.booking.application.service.impl;

import com.booking.application.service.UserServiceApplication;
import com.booking.domain.model.entity.User;

import java.util.Optional;

public class UserServiceApplicationImpl implements UserServiceApplication {
    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }
}
