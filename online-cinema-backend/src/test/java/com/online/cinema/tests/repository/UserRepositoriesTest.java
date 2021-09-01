package com.online.cinema.tests.repository;

import com.online.cinema.persist.Role;
import com.online.cinema.persist.User;
import com.online.cinema.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserRepositoriesTest {
    private final String USER_NAME_JOHNY = "Johny";
    private final String NONEXISTENT_USER_NAME = "Anna";
    private final String NONEXISTENT_USER_EMAIL = "anna_2001@gmail.com";
    private final String USER_EMAIL_JOHNY = "johny_johnsony@gmail.com";
    private final String USER_ROLE_JOHNY = "ROLE_ADMIN";
    private final Long USER_ID_ROLE_JOHNY = 1L;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByLoginTest() {
        Optional<User> userJohny = userRepository.findByLogin(USER_NAME_JOHNY);
        Optional<User> nonExistentUser = userRepository.findByEmail(NONEXISTENT_USER_NAME);
        Assertions.assertFalse(nonExistentUser.isPresent());
        Assertions.assertTrue(userJohny.isPresent());
        Assertions.assertEquals(USER_EMAIL_JOHNY, userJohny.get().getEmail());
        Role role = new Role();
        role.setName(USER_ROLE_JOHNY);
        role.setId(USER_ID_ROLE_JOHNY);
        Assertions.assertEquals(List.of(role), userJohny.get().getRoles());
    }

    @Test
    public void findByEmailTest() {
        Optional<User> userJohny = userRepository.findByEmail(USER_EMAIL_JOHNY);
        Optional<User> nonExistentUser = userRepository.findByEmail(NONEXISTENT_USER_EMAIL);
        Assertions.assertFalse(nonExistentUser.isPresent());
        Assertions.assertTrue(userJohny.isPresent());
        Assertions.assertEquals(USER_NAME_JOHNY, userJohny.get().getLogin());
        Role role = new Role();
        role.setName(USER_ROLE_JOHNY);
        role.setId(USER_ID_ROLE_JOHNY);
        Assertions.assertEquals(List.of(role), userJohny.get().getRoles());
    }

    @Test
    public void existsByLoginTest() {
        Optional<User> userJohny = userRepository.findByLogin(USER_NAME_JOHNY);
        Optional<User> nonExistentUser = userRepository.findByEmail(NONEXISTENT_USER_NAME);
        Assertions.assertFalse(nonExistentUser.isPresent());
        Assertions.assertTrue(userJohny.isPresent());
    }

    @Test
    public void existsByEmailTest() {
        Optional<User> userJohny = userRepository.findByEmail(USER_EMAIL_JOHNY);
        Optional<User> nonExistentUser = userRepository.findByEmail(NONEXISTENT_USER_EMAIL);
        Assertions.assertFalse(nonExistentUser.isPresent());
        Assertions.assertTrue(userJohny.isPresent());
    }
}
