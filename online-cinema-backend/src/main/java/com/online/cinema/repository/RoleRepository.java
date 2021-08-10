package com.online.cinema.repository;

import com.online.cinema.persist.ERole;
import com.online.cinema.persist.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
