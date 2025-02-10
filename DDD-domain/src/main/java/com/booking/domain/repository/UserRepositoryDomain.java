package com.booking.domain.repository;

import com.booking.domain.model.entity.User;
import com.booking.domain.model.entity.UserCreateDto;

import java.util.Optional;

public interface UserRepositoryDomain {
     Optional<User> findUserByUsername(String username);
     User createUser(UserCreateDto payload);
     Optional<User> findUserByEmail(String email);

}
