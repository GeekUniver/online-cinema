package com.online.cinema.persist;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "video_metadata_crew")
@Data
@NoArgsConstructor
public class CrewWithRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private VideoMetadata videoMetadata;

    @OneToOne
    @JoinColumn(name = "crew_classifier_id")
    private Crew crew;

    @OneToOne
    @JoinColumn(name = "crew_role_classifier_id")
    private CrewRole crewRole;
}
