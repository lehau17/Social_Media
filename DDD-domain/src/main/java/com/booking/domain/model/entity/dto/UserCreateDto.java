package com.booking.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    private String username;
    private String password;
    private String email;
}
