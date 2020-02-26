package com.github.arakorno.stackapi.service.impl;

import com.github.arakorno.stackapi.entity.User;
import com.github.arakorno.stackapi.exception.UserNotFoundException;
import com.github.arakorno.stackapi.model.UserModel;
import com.github.arakorno.stackapi.service.StackexchangeApiService;
import com.github.arakorno.stackapi.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final StackexchangeApiService stackexchangeApiService;

    @Override
    @Cacheable(value = "userDetails", cacheManager = "cacheManager")
    public User getUserDetails(Integer userId) {
        UserModel userModel = stackexchangeApiService.getUserDetails(userId);
        return userModel.getUserItems().stream().findFirst().map(User::of)
                .orElseThrow(() -> new UserNotFoundException(format("User with id %d not found!", userId)));
    }
}
