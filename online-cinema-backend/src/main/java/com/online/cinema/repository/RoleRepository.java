package com.online.cinema.repository;

import com.online.cinema.persist.ERole;
import com.online.cinema.persist.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Collection<Role> findByName(String name);
}
