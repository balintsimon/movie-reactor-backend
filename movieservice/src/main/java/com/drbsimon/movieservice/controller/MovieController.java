package com.drbsimon.movieservice.controller;

import com.drbsimon.movieservice.entity.Movie;
import com.drbsimon.movieservice.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MovieController {
    private final MovieRepository movieRepository;

    @GetMapping("/scheduled-movies")
    public List<Movie> getAllScheduledMovie() {
        return movieRepository.findAll();
    }

    @GetMapping("/movie")
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/movie/{id}")
    public Movie getMovieById(@RequestParam("id") Long id) {
        return movieRepository.getById(id);
    }
}