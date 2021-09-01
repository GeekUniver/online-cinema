package com.online.cinema.tests.service;

import com.online.cinema.persist.User;
import com.online.cinema.repository.UserRepository;
import com.online.cinema.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest(classes = UserService.class)
public class UserServiceTest {
    private final Long DEMO_USER_ID = 1L;
    private final String DEMO_USER_NAME = "Alina";
    private final String DEMO_USER_EMAIL = "hero@gmail.com";

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void findByIdTest(){
        User demoUser = new User();
        demoUser.setId(DEMO_USER_ID);
        demoUser.setLogin(DEMO_USER_NAME);
        demoUser.setEmail(DEMO_USER_EMAIL);

        Mockito
                .doReturn(Optional.of(demoUser))
                .when(userRepository)
                .findById(DEMO_USER_ID);

        User user = userService.findById(DEMO_USER_ID);

        Mockito.verify(userRepository, Mockito.times(1)).findById(ArgumentMatchers.eq(DEMO_USER_ID));
        Assertions.assertEquals("Alina", user.getLogin());
    }

    @Test
    @Order(2)
    public void findByEmailTest(){
        User demoUser = new User();
        demoUser.setId(DEMO_USER_ID);
        demoUser.setLogin(DEMO_USER_NAME);
        demoUser.setEmail(DEMO_USER_EMAIL);

        Mockito
                .doReturn(Optional.of(demoUser))
                .when(userRepository)
                .findByEmail(DEMO_USER_EMAIL);

        User user = userService.findByEmail(DEMO_USER_EMAIL);

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(ArgumentMatchers.eq(DEMO_USER_EMAIL));
        Assertions.assertEquals("Alina", user.getLogin());
    }
}
