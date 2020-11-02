package com.drbsimon.moviecatalog.service;

import com.drbsimon.moviecatalog.model.dto.MovieDTO;
import com.drbsimon.moviecatalog.model.wrapper.MovieListWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin
@Service
@Slf4j
@RequiredArgsConstructor
public class MovieServiceCaller {
    private final RestTemplate restTemplate;

    @Value("${movieservice.url}")
    private String movieBaseUrl;

    public List<MovieDTO> getAllMovies() {
        MovieListWrapper movieListWrapper = restTemplate.getForObject(movieBaseUrl + "/movie", MovieListWrapper.class);
        return movieListWrapper.getMovies();
    }

    public MovieDTO getMovieByMovieId(Long id) {
        return restTemplate.getForObject(movieBaseUrl + "/movie/" + id, MovieDTO.class);
    }
}
