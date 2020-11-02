package com.drbsimon.movieservice.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class MovieListWrapper {
    List<Movie> movies;
}

