package com.drbsimon.moviecatalog.controller;

import com.drbsimon.moviecatalog.model.Show;
import com.drbsimon.moviecatalog.model.wrapper.MovieListWrapper;
import com.drbsimon.moviecatalog.model.wrapper.ShowListWrapper;
import com.drbsimon.moviecatalog.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ShowController {
    private final ShowService showService;

    // TODO: make it check timeframe => show upcoming daily shows only
    @GetMapping("/schedule")
    public ShowListWrapper getCurrentShows() {
        return showService.getShowsForAWeekFromNow();
    }

    @GetMapping("/show")
    public ShowListWrapper getAllShows() {
        return showService.getAllShows();
    }

    @GetMapping("/show/movie")
    public MovieListWrapper getAllMovies() {
        return showService.getAllMoviesOnShow();
    }

    @GetMapping("/show/{showId}")
    public Show getShowById(@PathVariable("showId") Long showId) {
        return showService.getShowById(showId);
    }

    @PutMapping("/show/{showId}")
    public void updateShowStartingTime(@PathVariable("showId") Long showId) {
        // TODO: Need to implement, use PathVariable
//        showManager
    }

    // TODO: implement posting new show
//    @PostMapping("/show")
//    public Show addNewShow() {
//
//    }

    // TODO: check if id may travel in request body
    @DeleteMapping("/show/{showId}")
    public void deleteShowById(@PathVariable("showId") Long showId) {
        showService.deleteShowById(showId);
    }

    // TODO: check RESTfulness!
    // TODO: check if id may travel in request body
    @DeleteMapping("/show/movie/{showId}")
    public void deleteShowByMovieId(@PathVariable("showId") Long movieId) {
        showService.deleteShowByMovieId(movieId);
    }
}