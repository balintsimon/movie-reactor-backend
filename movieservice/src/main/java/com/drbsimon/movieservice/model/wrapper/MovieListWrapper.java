package com.drbsimon.movieservice.model.wrapper;

import com.drbsimon.movieservice.model.Movie;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class MovieListWrapper {
    List<Movie> movies;
}

