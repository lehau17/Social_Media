package com.booking.infrastructure.persistence.repository;

import com.booking.domain.model.entity.User;
import com.booking.infrastructure.persistence.mapper.UserJPAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailSecurityRepository implements UserDetailsService {

    @Autowired
    private UserJPAMapper userJPAMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJPAMapper.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPasswordHash()) // Không cần nếu không xác thực password ở đây
                .authorities("ROLE_USER") // Bạn có thể lấy roles từ database
                .build();
    }
}
