package com.booking.application.service.impl;

import com.booking.application.mapper.UserMapper;
import com.booking.application.model.UserResponse;
import com.booking.application.service.UserServiceApplication;
import com.booking.domain.model.entity.User;
import com.booking.domain.model.entity.UserCreateDto;
import com.booking.domain.service.UserServiceDomain;
import com.booking.infrastructure.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceApplicationImpl implements UserServiceApplication {
    @Autowired
    private UserServiceDomain userServiceDomain;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userServiceDomain.findByUsername(username);
    }
    /**
     * Return response with 2 token
     * Param : user
     * */
    private UserResponse returnUserResponseWithToken(User user) {
         return  UserMapper.toUserLoginResponse(user)
                .setAccessToken(jwtProvider
                        .generateAccessToken(user.getId(), user.getUsername(), user.getEmail(), new String[]{"ROLE_USER"})
                )
                .setRefreshToken(jwtProvider
                        .generateRefreshToken(user.getId(), user.getUsername(), user.getEmail(), new String[]{"ROLE_USER"})
                );
    }

    @Override
    public UserResponse login(String username, String password) throws Exception {
        User foundUser = this.userServiceDomain.login(username, password).orElseThrow(()-> new Exception("User Not Found"));
        return returnUserResponseWithToken(foundUser);
    }

    @Override
    public UserResponse register(UserCreateDto user) throws Exception {
        var newUser = userServiceDomain.save(user);
        return returnUserResponseWithToken(newUser);

    }
}
