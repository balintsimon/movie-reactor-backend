package com.drbsimon.movieservice.controller;

import com.drbsimon.movieservice.entity.Movie;
import com.drbsimon.movieservice.model.MovieListWrapper;
import com.drbsimon.movieservice.repository.MovieManager;
import com.drbsimon.movieservice.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.security.AlgorithmConstraints;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final MovieManager movieManager;

    //  TODO: cannot show in itself which movies are on air in a time frame, needs information
    //   from show service to do that => can combine them in API gateway
    // TODO: rework frontend to reflect change
//    @GetMapping("/scheduled-movies")
//    public List<Movie> getAllScheduledMovie() {
//        return movieManager.getAllMovies();
//    }

    @GetMapping
    public MovieListWrapper getAllMoviesWrapped() {
        return movieManager.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable("id") Long id) {
        return movieManager.getMovieById(id);
    }
}