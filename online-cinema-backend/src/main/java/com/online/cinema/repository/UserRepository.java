package com.online.cinema.repository;

import com.online.cinema.persist.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Optional<User> findByEmail(String email);
    Boolean existsByLogin(String login);
    Boolean existsByEmail(String email);
}
