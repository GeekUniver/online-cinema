package com.online.cinema.controller.repr;

import com.online.cinema.persist.Crew;
import com.online.cinema.persist.CrewRole;
import lombok.Data;


@Data
public class CrewWithRoleRepr {
    public CrewWithRoleRepr(Long id, Crew crew, CrewRole crewRole) {
        this.id = id;
        this.crew = crew;
        this.crewRole = crewRole;
    }

    private Long id;

    private Crew crew;

    private CrewRole crewRole;
}
