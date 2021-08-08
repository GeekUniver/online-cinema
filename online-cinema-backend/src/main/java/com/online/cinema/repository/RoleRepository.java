package com.online.cinema.repository;

import com.online.cinema.persist.ERole;
import com.online.cinema.persist.Role;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
    Optional<Role> findByName(ERole name);
}
