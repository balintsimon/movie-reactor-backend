package com.drbsimon.movieservice.service;

import com.drbsimon.movieservice.model.Movie;
import com.drbsimon.movieservice.model.wrapper.MovieListWrapper;
import com.drbsimon.movieservice.repository.MovieRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieListWrapper getAllMovies() {
        MovieListWrapper wrapper = new MovieListWrapper();
        List<Movie> movies = movieRepository.findAll();
        wrapper.setMovies(movies);
        return wrapper;
    }

    public Movie getMovieById(Long movieId) {
        return movieRepository.getById(movieId);
    }

}
