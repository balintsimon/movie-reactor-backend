package com.drbsimon.moviecatalog.controller;

import com.drbsimon.moviecatalog.entity.Show;
import com.drbsimon.moviecatalog.model.ShowListWrapper;
import com.drbsimon.moviecatalog.repository.ShowManager;
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
    private final ShowManager showManager;

    // TODO: make it check timeframe => show upcoming daily shows only
    @GetMapping("/schedule")
    public ShowListWrapper getCurrentShows() {
        return showManager.getAllCurrentShows();
    }

    @GetMapping("/show")
    public ShowListWrapper getAllShows() {
        return showManager.getAllShows();
    }

    @GetMapping("/show/{showId}")
    public Show getShowById(@PathVariable("showId") Long showId) {
        return showManager.getShowById(showId);
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
        showManager.deleteShowById(showId);
    }

    // TODO: check RESTfulness!
    // TODO: check if id may travel in request body
    @DeleteMapping("/show/movie/{showId}")
    public void deleteShowByMovieId(@PathVariable("showId") Long movieId) {
        showManager.deleteShowByMovieId(movieId);
    }
}