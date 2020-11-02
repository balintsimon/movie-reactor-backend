package com.drbsimon.movieservice.controller;

import com.drbsimon.movieservice.model.Movie;
import com.drbsimon.movieservice.model.wrapper.MovieListWrapper;
import com.drbsimon.movieservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public MovieListWrapper getAllMoviesWrapped() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable("id") Long id) {
        return movieService.getMovieById(id);
    }
}