package com.drbsimon.movieservice;

import com.drbsimon.movieservice.entity.Movie;
import com.drbsimon.movieservice.model.MovieListWrapper;
import com.drbsimon.movieservice.repository.MovieManager;
import com.drbsimon.movieservice.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@ComponentScan(basePackages = {"com.drbsimon.movieservice"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MovieserviceApplicationTests {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private MovieManager manager;

    @Test
    public void testRepositoryLoads() throws Exception {
        assertThat(repository).isNotNull();
    }

    @Test
    public void testManagerLoads() throws Exception {
        assertThat(manager).isNotNull();
    }

    @Test
    public void testSaveOneMovieSimple() {
        Movie movie = Movie.builder()
                .movieDbId(1234)
                .build();

        repository.save(movie);

        List<Movie> movies = repository.findAll();
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
        repository.saveAll(Arrays.asList(movie1, movie2, movie3));

        List<Movie> movies = repository.findAll();
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
        repository.saveAll(Arrays.asList(movie1, movie2, movie3));

        List<Movie> movies = repository.findAll();
        Movie expectedResult = movies.get(0);
        Long sampleId = expectedResult.getId();
        Movie actualResult = repository.getById(sampleId);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    public void testNoMovieDbIdThrowsError() {
        Movie movie = Movie.builder()
                .build();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(movie);
            repository.findAll();
        });
    }

    @Test
    public void testManagerForOne() {
        Movie movie1 = Movie.builder()
                .movieDbId(1234)
                .build();

        Movie movie2 = Movie.builder()
                .movieDbId(5678)
                .build();

        Movie movie3 = Movie.builder()
                .movieDbId(910)
                .build();
        repository.saveAll(Arrays.asList(movie1, movie2, movie3));

        List<Movie> movies = repository.findAll();
        Movie expectedResult = movies.get(0);
        Long sampleId = expectedResult.getId();
        Movie actualResult = manager.getMovieById(sampleId);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    public void testManagerForList() {
        Movie movie1 = Movie.builder()
                .movieDbId(1234)
                .build();

        Movie movie2 = Movie.builder()
                .movieDbId(5678)
                .build();

        Movie movie3 = Movie.builder()
                .movieDbId(910)
                .build();
        repository.saveAll(Arrays.asList(movie1, movie2, movie3));

        List<Movie> expectedResult = repository.findAll();
        MovieListWrapper wrapper = manager.getAllMovies();
        List<Movie> actualResult = wrapper.getMovies();

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
