package com.booking.controller.http;

import com.booking.application.model.UserResponse;
import com.booking.application.service.UserServiceApplication;
import com.booking.controller.model.enums.ResultUtil;
import com.booking.controller.model.v0.ResultMessage;
import com.booking.domain.model.entity.User;
import com.booking.domain.model.entity.UserCreateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("auth")
@Slf4j
public class UserController {
    @Autowired
    private UserServiceApplication userServiceApplication;

    @GetMapping()
    public ResultMessage<Optional<User>> findByUsername(@RequestParam(name = "username", required = false) String username) {
        return ResultUtil.data(userServiceApplication.findByUsername(username));
    }


    @GetMapping("login")
    public ResultMessage<UserResponse> login(@RequestParam(name = "username", required = false) String username, @RequestParam(name = "password", required = false) String password) throws Exception {
           return ResultUtil.data(this.userServiceApplication.login(username, password));
    }


    @GetMapping("register")
    public ResponseEntity<ResultMessage<UserResponse>> register(@RequestBody() UserCreateDto payload) throws Exception {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResultUtil
                        .data(this
                                .userServiceApplication
                                .register(payload))
                        .setCode(HttpStatus
                                    .CREATED
                                    .value()
                        )
                );
    }
}
