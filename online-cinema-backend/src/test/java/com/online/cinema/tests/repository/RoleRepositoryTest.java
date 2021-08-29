package com.online.cinema.tests.repository;

import com.online.cinema.persist.Role;
import com.online.cinema.persist.User;
import com.online.cinema.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class RoleRepositoryTest {
    private final String USER_NAME_JOHNY = "Johny";
    private final String NONEXISTENT_USER_ROLE = "ROLE_DRAGON";
    private final String USER_EMAIL_JOHNY = "johny_johnsony@gmail.com";
    private final String USER_ROLE_JOHNY = "ROLE_ADMIN";
    private final Long USER_ID_ROLE_JOHNY = 1L;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findByNameTest(){
        List<Role> roleListJohny = roleRepository.findByName(USER_ROLE_JOHNY);
        List<Role> roleListAnna = roleRepository.findByName(NONEXISTENT_USER_ROLE);
        Assertions.assertFalse(roleListJohny.isEmpty());
        Assertions.assertTrue(roleListAnna.isEmpty());
        User johny = new User();
        johny.setLogin(USER_NAME_JOHNY);
        johny.setEmail(USER_EMAIL_JOHNY);
        Role role = new Role();
        role.setName(USER_ROLE_JOHNY);
        role.setId(USER_ID_ROLE_JOHNY);
        johny.setRoles(List.of(role));
        Assertions.assertEquals(johny.getRoles(), roleListJohny);
    }
}
