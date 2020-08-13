package com.drbsimon.moviecatalog.controller;

import com.drbsimon.moviecatalog.entity.Show;
import com.drbsimon.moviecatalog.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
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
}