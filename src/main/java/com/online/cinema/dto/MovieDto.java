package com.online.cinema.dto;

import com.online.cinema.model.Movie;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class MovieDto implements Serializable {
    private Long id;
    private String title;
    private int price;
    private String country;
    private String director;
    private String genre;
    private int year;

    public MovieDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.price = movie.getPrice();
        this.country = movie.getCountry();
        this.director = movie.getDirector();
        this.genre = movie.getGenre();
        this.year = movie.getYear();
    }
}
