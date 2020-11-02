package com.drbsimon.movieservice.controller;

import com.drbsimon.movieservice.model.Movie;
import com.drbsimon.movieservice.model.wrapper.MovieListWrapper;
import com.drbsimon.movieservice.service.MovieService;
import com.drbsimon.movieservice.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.List;


@WebMvcTest(controllers = MovieController.class)
@ActiveProfiles("test")
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // TODO: delete after service dependency is deleted (data initializer is deleted from entry point)
    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private MovieService service;

    private List<Movie> movieList;
    private MovieListWrapper movieListWrapper;

    @BeforeEach
    void setUp() {
        this.movieList = new ArrayList<>();
        this.movieList.add(new Movie(1L, 123, 100));
        this.movieList.add(new Movie(2L, 234, 100));
        this.movieList.add(new Movie(3L, 345, 100));

        this.movieListWrapper = new MovieListWrapper();
        movieListWrapper.setMovies(movieList);
    }

    @Test
    public void testFetchAllFromWrapper() throws Exception {
        given(service.getAllMovies()).willReturn(movieListWrapper);

        this.mockMvc.perform(get("/movie"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movies.size()", is(movieList.size())));
    }

    @Test
    public void testFetchOne() throws Exception {
        final long movieId = 1L;
        final int movieDbId = 999;
        final Movie movie = new Movie(movieId, movieDbId, 100);
        given(service.getMovieById(movieId)).willReturn(movie);

        this.mockMvc.perform(get("/movie/" + movieId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) movieId)))
                .andExpect(jsonPath("$.movieDbId", is(movieDbId)));
    }
}