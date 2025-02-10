package com.booking.domain.service.impl;

import com.booking.domain.model.entity.User;
import com.booking.domain.model.entity.UserCreateDto;
import com.booking.domain.repository.UserRepositoryDomain;
import com.booking.domain.service.UserServiceDomain;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceDomainImpl implements UserServiceDomain {
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepositoryDomain userRepositoryDomain;
    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepositoryDomain.findUserByUsername(username);
    }

    @Override
    public Optional<User> login(String username, String password) throws Exception {
        Optional<User> foundUser = this.userRepositoryDomain.findUserByUsername(username);
        if (foundUser.isEmpty()) {
            throw new BadRequestException("User deo ton tai");
        }
        User user = foundUser.get();
        if(!this.passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BadRequestException("Wrong password");
        }
        return foundUser;
    }

    @Override
    public User save(UserCreateDto user) throws Exception {
        // check user in database with username
        Optional<User> foundUser = this.userRepositoryDomain.findUserByUsername(user.getUsername());
        if (foundUser.isPresent()) {
            throw new BadRequestException("User đã tồn tại");
        }
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepositoryDomain.createUser(user);
    }
}
