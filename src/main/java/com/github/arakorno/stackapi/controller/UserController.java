package com.github.arakorno.stackapi.controller;

import com.github.arakorno.stackapi.entity.User;
import com.github.arakorno.stackapi.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/{id}")
    public User getUserDetails(@PathVariable("id") Integer id) {
        return userDetailsService.getUserDetails(id);
    }
}
