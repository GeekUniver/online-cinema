package com.online.cinema.controller.repr;

import com.online.cinema.persist.Country;
import com.online.cinema.persist.CrewWithRole;
import com.online.cinema.persist.Genre;
import com.online.cinema.persist.VideoMetadata;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class VideoMetadataRepr {

    private Long id;

    private String fileName;

    private String contentType;

    private String description;

    private Long fileSize;

    private Long videoLength;

    private String name;

    private Integer year_filmed;

    /*Список жанров фильма*/
    private List<Genre> genreList;

    /*Страны производства фильма*/
    private List<Country> countryList;

    /*Съемочная группа*/
    private List<CrewWithRole> crewWithRole;

    private String previewUrl;

    private String streamUrl;

    public VideoMetadataRepr(VideoMetadata vmd) {
        this.id = vmd.getId();
        this.fileName = vmd.getFileName();
        this.contentType = vmd.getContentType();
        this.description = vmd.getDescription();
        this.fileSize = vmd.getFileSize();
        this.videoLength = vmd.getVideoLength();
        this.name = vmd.getName();
        this.year_filmed = vmd.getYear_filmed();
        this.genreList = vmd.getGenreList();
        this.countryList = vmd.getCountryList();
        this.crewWithRole = vmd.getCrewWithRole();
    }
}
