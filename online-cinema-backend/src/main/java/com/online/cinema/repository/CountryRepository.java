package com.online.cinema.repository;

import com.online.cinema.persist.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
