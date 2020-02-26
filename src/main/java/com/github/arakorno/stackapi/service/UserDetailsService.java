package com.github.arakorno.stackapi.service;

import com.github.arakorno.stackapi.entity.User;
import org.springframework.cache.annotation.Cacheable;

public interface UserDetailsService {
    String USER_NOT_FOUND_ERROR = "User with id %d not found!";

    @Cacheable(value = "user_details", key = "userId", cacheManager = "cacheManager")
    User getUserDetails(Integer userId);
}
