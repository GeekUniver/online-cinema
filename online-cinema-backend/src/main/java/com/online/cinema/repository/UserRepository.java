package com.online.cinema.repository;

import com.online.cinema.persist.User;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Optional<User> findByEmail(String email);
    Boolean existsByLogin(String username);
    Boolean existsByEmail(String email);
}
