package com.online.cinema.services;

import com.online.cinema.model.Movie;
import com.online.cinema.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie saveOrUpdate(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovieById(Long id){
        movieRepository.deleteById(id);
    }
}
