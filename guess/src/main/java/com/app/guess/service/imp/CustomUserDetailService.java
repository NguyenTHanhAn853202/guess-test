package com.app.guess.service.imp;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.guess.dto.UserDetailsDto;
import com.app.guess.mapper.UserMapper;
import com.app.guess.model.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsDto user = userMapper.loadUserbyUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("");

        return CustomUserDetails.builder().userId(user.getUserId()).username(user.getUsername())
                .password(user.getPassword()).build();
    }

}
