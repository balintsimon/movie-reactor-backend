package com.drbsimon.moviecatalog.controller;

import com.drbsimon.moviecatalog.entity.Show;
import com.drbsimon.moviecatalog.repository.ShowRepository;
import com.drbsimon.moviecatalog.service.MovieServiceCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin//("http://localhost:8762")
@RestController
@RequiredArgsConstructor
public class ShowController {
    private final ShowRepository showRepository;

    // TODO: make it check timeframe => show upcoming daily shows only
    @GetMapping("/schedule")
    public List<Show> getCurrentShows() {
        return showRepository.findAll();
    }

    @GetMapping("/show")
    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    @GetMapping("/show/{showId}")
    public Show getShowById(@PathVariable("showId") Long showId) {
        return showRepository.getShowById(showId);
    }

    @PutMapping("/show/{showId}")
    public void updateShowStartingTime(@PathVariable("showId") Long showId) {
        // TODO: Need to implement, use PathVariable
    }

    // TODO: implement posting new show
//    @PostMapping("/show")
//    public Show addNewShow() {
//
//    }

    // TODO: check if id may travel in request body
    @DeleteMapping("/show/{showId}")
    public void deleteShowById(@PathVariable("showId") Long showId) {
        showRepository.deleteById(showId);
    }

    // TODO: check RESTfulness!
    // TODO: check if id may travel in request body
    @DeleteMapping("/show/movie/{showId}")
    public void deleteShowByMovieId(@PathVariable("showId") Long movieId) {
        showRepository.deleteByMovieId(movieId);
    }
}