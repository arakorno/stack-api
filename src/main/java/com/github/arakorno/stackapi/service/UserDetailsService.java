package com.github.arakorno.stackapi.service;

import com.github.arakorno.stackapi.entity.User;
import org.springframework.cache.annotation.Cacheable;

public interface UserDetailsService {
    @Cacheable(value = "user_details", key = "userId", cacheManager = "cacheManager")
    User getUserDetails(Integer userId);
}
