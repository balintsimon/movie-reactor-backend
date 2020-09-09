package com.drbsimon.movieservice;

import com.drbsimon.movieservice.entity.Movie;
import com.drbsimon.movieservice.model.MovieListWrapper;
import com.drbsimon.movieservice.repository.MovieManager;
import com.drbsimon.movieservice.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({SpringExtension.class})
@DataJpaTest
@ActiveProfiles("test")
class MovieserviceApplicationTests {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void testSaveOneMovieSimple() {
        Movie movie = Movie.builder()
                .movieDbId(1234)
                .build();

        movieRepository.save(movie);
        List<Movie> movies = movieRepository.findAll();
        assertThat(movies).hasSize(1);
    }

    @Test
    public void testSaveSeveralMovieSimple() {
        Movie movie1 = Movie.builder()
                .movieDbId(1234)
                .build();

        Movie movie2 = Movie.builder()
                .movieDbId(5678)
                .build();

        Movie movie3 = Movie.builder()
                .movieDbId(910)
                .build();
        movieRepository.saveAll(Arrays.asList(movie1, movie2, movie3));
        List<Movie> movies = movieRepository.findAll();
        assertThat(movies).hasSize(3);
    }

    @Test
    public void testFindById() {
        Movie movie1 = Movie.builder()
                .movieDbId(1234)
                .build();

        Movie movie2 = Movie.builder()
                .movieDbId(5678)
                .build();

        Movie movie3 = Movie.builder()
                .movieDbId(910)
                .build();
        movieRepository.saveAll(Arrays.asList(movie1, movie2, movie3));
        List<Movie> movies = movieRepository.findAll();
        Movie expectedResult = movies.get(0);
        Movie actualResult = movieRepository.getById(expectedResult.getId());
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    public void testNoMovieDbIdThrowsError() {
        Movie movie = Movie.builder()
                .build();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            movieRepository.save(movie);
            List<Movie> movies = movieRepository.findAll();
        });
    }
}
