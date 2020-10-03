package com.drbsimon.movieservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/moviedb")
public class MovieDBPassThrough {
    @LoadBalanced
    RestTemplate restTemplate = new RestTemplate();

    @Value("${moviedb.url}")
    String targetBaseUrl;

    @Value("${moviedb_api_key}")
    String api_key;

    @GetMapping("/movie/{restUrlPath}")
    @ResponseBody
    public ResponseEntity<String> GetMovieDetails(@PathVariable("restUrlPath") String restUrlPath) {
        String URL = targetBaseUrl+ "/movie/" + restUrlPath + "?api_key=" + api_key;
        return restTemplate.getForEntity(URL, String.class);
    }

    @GetMapping("/movie/{restUrlPath}/videos")
    @ResponseBody
    public ResponseEntity<String> GetVideos(@PathVariable("restUrlPath") String restUrlPath) {
        String URL = targetBaseUrl+ "/movie/" + restUrlPath + "/videos?api_key=" + api_key;
        return restTemplate.getForEntity(URL, String.class);
    }

    @GetMapping("/movie/{restUrlPath}/credits")
    @ResponseBody
    public ResponseEntity<String> GetCredits(@PathVariable("restUrlPath") String restUrlPath) {
        String URL = targetBaseUrl+ "/movie/" + restUrlPath + "/credits?api_key=" + api_key;
        return restTemplate.getForEntity(URL, String.class);
    }

    @GetMapping("/person/{restUrlPath}")
    @ResponseBody
    public ResponseEntity<String> GetPersonDetails(@PathVariable("restUrlPath") String restUrlPath) {
        String URL = targetBaseUrl+ "/person/" + restUrlPath + "?api_key=" + api_key;
        return restTemplate.getForEntity(URL, String.class);
    }

    @GetMapping("/person/{restUrlPath}/movie_credits")
    @ResponseBody
    public ResponseEntity<String> GetMovieCredits(@PathVariable("restUrlPath") String restUrlPath) {
        String URL = targetBaseUrl+ "/person/" + restUrlPath + "/movie_credits?api_key=" + api_key;
        return restTemplate.getForEntity(URL, String.class);
    }
}
