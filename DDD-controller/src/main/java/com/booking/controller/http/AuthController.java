package com.booking.controller.http;

import com.booking.application.model.JwtResponse;
import com.booking.application.model.UserCreateApplicationDto;
import com.booking.application.model.UserLoginDto;
import com.booking.application.model.UserResponse;
import com.booking.application.service.UserServiceApplication;
import com.booking.controller.model.enums.ResultUtil;
import com.booking.controller.model.v0.ResultMessage;
import com.booking.domain.model.entity.User;
import com.booking.domain.model.entity.UserCreateDto;
import jakarta.validation.Valid;
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


    @PostMapping("login")
    public ResultMessage<UserResponse> login(@Valid @RequestBody(required = true) UserLoginDto body) throws Exception {
           return ResultUtil.data(this.userServiceApplication.login(body.getUsername(), body.getPassword()));
    }

    @PostMapping("refresh-token")
    public ResultMessage<JwtResponse> login(@RequestHeader(name = "x-refresh-token") String refreshToken) throws Exception {
        log.info("check refresh token : {}", refreshToken);
        return ResultUtil.data(this.userServiceApplication.refreshToken(refreshToken));
    }


    @PostMapping("register")
    public ResponseEntity<ResultMessage<UserResponse>> register(@Valid @RequestBody() UserCreateApplicationDto payload) throws Exception {
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
