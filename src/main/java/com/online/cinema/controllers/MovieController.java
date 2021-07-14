package com.online.cinema.controllers;

import com.online.cinema.model.Movie;
import com.online.cinema.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public List<Movie> findAllMovies() {
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public Movie findMovieById(@PathVariable Long id) {
        return movieService.findMovieById(id).get();
    }

    @PostMapping
    public Movie saveNewMovie(@RequestBody Movie movie) {
        movie.setId(null);
        return movieService.saveOrUpdate(movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovieById(@PathVariable Long id) {
        movieService.deleteMovieById(id);
    }
}
