package com.online.cinema.persist;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "video_metadata")
@Data
@NoArgsConstructor
public class VideoMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fileName;

    @Column
    private String contentType;

    @Column
    private String description;

    @Column
    private Long fileSize;

    @Column
    private Long videoLength;

    @Column
    private String name;

    @Column
    private Integer year_filmed;

    /*Список жанров фильма*/
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "video_metadata_genre",
            joinColumns = @JoinColumn(name = "video_metadata_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_classifier_id")
    )
    private List<Jenre> jenreList;

    /*Страны производства фильма*/
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "video_metadata_country",
            joinColumns = @JoinColumn(name = "video_metadata_id"),
            inverseJoinColumns = @JoinColumn(name = "country_classifier_id")
    )
    private List<Country> countryList;

    /*Съемочная группа*/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "videoMetadata")
    private List<CrewWithRole> crewWithRole;

}

