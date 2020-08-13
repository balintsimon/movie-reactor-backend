package com.drbsimon.moviecatalog.service;

import com.drbsimon.moviecatalog.model.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieServiceCaller {

    private final RestTemplate restTemplate;

    @Value("http://movieservice/movieservice")
    private String movieBaseUrl;

    public List<Movie> getAllShows() {
        return restTemplate.getForObject(movieBaseUrl + "/room", List.class);
    }
}
