package com.booking.infrastructure.persistence.repository;

import com.booking.domain.model.entity.User;
import com.booking.domain.model.entity.UserCreateDto;
import com.booking.domain.repository.UserRepositoryDomain;
import com.booking.infrastructure.persistence.mapper.UserJPAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryDomainImpl implements UserRepositoryDomain {
    @Autowired
    private UserJPAMapper userJPAMapper;

    public Optional<User> findUserByUsername(String username){
        return this.userJPAMapper.findByUsername(username);
    }


    public User createUser(UserCreateDto payload){
        var newUser = new User()
                .setUsername(payload.getUsername())
                .setPasswordHash(payload.getPassword())
                .setEmail(payload.getEmail());
        return this.userJPAMapper.save(newUser);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return this.userJPAMapper.findByEmail(email);
    }
}
