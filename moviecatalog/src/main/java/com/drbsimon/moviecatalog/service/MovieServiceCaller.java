package com.drbsimon.moviecatalog.service;

import com.drbsimon.moviecatalog.model.Movie;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    // YAGNI: would have been used to initiate DB
    public List<Movie> getAllMovies() {
        return restTemplate.getForObject(movieBaseUrl + "/movie", List.class);
    }
}
