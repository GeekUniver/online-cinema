package com.online.cinema.controller.repr;

import com.online.cinema.persist.Country;
import com.online.cinema.persist.CrewWithRole;
import com.online.cinema.persist.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Data
public class NewVideoRepr {
    private String description;
    private MultipartFile file;

    private String name;

    private Integer year_filmed;


    /*Список жанров фильма*/

    private List<String> genreList;

    /*Страны производства фильма*/

    private List<String> countryList;

    /*Съемочная группа*/
    private List<CrewWithRole> crewWithRole;
}
