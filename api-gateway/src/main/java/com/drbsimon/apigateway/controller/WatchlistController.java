package com.drbsimon.apigateway.controller;

import com.drbsimon.apigateway.wrapper.WatchListWrapper;
import com.drbsimon.apigateway.repository.WatchlistManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "${main.route}")
@RequiredArgsConstructor
@RequestMapping("/watchlist")
@RestController()
@Slf4j
public class WatchlistController {
    private final WatchlistManager watchlistManager;

    @GetMapping
    public WatchListWrapper getWatchListByUser() {
        return watchlistManager.getWatchlistByUsername();
    }

    @PostMapping("/{movie_db_id}")
    public boolean saveMovieIntoWatchList(@PathVariable Integer movie_db_id) {
        return watchlistManager.saveNewWatchlistElement(movie_db_id);
    }

    @DeleteMapping("/{movie_db_id}")
    public boolean deleteMovieFromWatchList(@PathVariable Integer movie_db_id) {
        return watchlistManager.deleteMovieFromWatchListById(movie_db_id);
    }
}
