package com.drbsimon.movieservice.repository;

import com.drbsimon.movieservice.entity.Movie;
import com.drbsimon.movieservice.model.MovieListWrapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class MovieManager {
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
