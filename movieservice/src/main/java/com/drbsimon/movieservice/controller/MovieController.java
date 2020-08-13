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

    //  TODO: get rid of this: cannot show in itself which movies are on air in a time frame,
    //  needs information from show service to do that => can combine them in API gateway
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