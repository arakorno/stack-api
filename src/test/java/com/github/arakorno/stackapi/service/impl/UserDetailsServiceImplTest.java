package com.github.arakorno.stackapi.service.impl;

import com.github.arakorno.stackapi.entity.User;
import com.github.arakorno.stackapi.exception.UserNotFoundException;
import com.github.arakorno.stackapi.model.UserModel;
import com.github.arakorno.stackapi.service.StackexchangeApiService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.github.arakorno.stackapi.service.UserDetailsService.USER_NOT_FOUND_ERROR;
import static java.lang.String.format;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private StackexchangeApiService stackexchangeApiService;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void getUserDetails() {
        UserModel response = UserModel.builder().userItems(List.of(UserModel.UserItem.builder().userId(1)
                .displayName("superuser").creationDate("2008-07-31T17:22:31+0300").build())).build();
        when(stackexchangeApiService.getUsers(1)).thenReturn(response);
        User res = userDetailsService.getUserDetails(1);
        Assert.assertEquals("superuser", res.getDisplayName());
    }

    @Test
    public void getUserDetails_failed() {
        when(stackexchangeApiService.getUsers(1)).thenReturn(UserModel.builder().userItems(List.of()).build());
        exception.expect(UserNotFoundException.class);
        exception.expectMessage(format(USER_NOT_FOUND_ERROR, 1));
        userDetailsService.getUserDetails(1);
    }
}