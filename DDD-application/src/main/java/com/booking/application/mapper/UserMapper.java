package com.booking.application.mapper;

import com.booking.application.model.UserResponse;
import com.booking.domain.model.entity.User;
import org.springframework.beans.BeanUtils;

public class UserMapper {
    public static UserResponse toUserLoginResponse(User user) {
        UserResponse userLoginResponse = new UserResponse();
        BeanUtils.copyProperties(user, userLoginResponse);
        return userLoginResponse;
    }



}
