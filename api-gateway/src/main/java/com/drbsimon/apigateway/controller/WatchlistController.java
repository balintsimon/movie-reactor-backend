package com.drbsimon.apigateway.controller;

import com.drbsimon.apigateway.model.dto.WatchListDTO;
import com.drbsimon.apigateway.service.WatchlistManager;
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
    public WatchListDTO getWatchListByUser() {
        return watchlistManager.getVisitorWatchlist();
    }

    @PostMapping("/{movie_db_id}")
    public boolean saveMovieIntoWatchList(@PathVariable Integer movie_db_id) {
        return watchlistManager.addToWatchList(movie_db_id);
    }

    @DeleteMapping("/{movie_db_id}")
    public boolean deleteMovieFromWatchList(@PathVariable Integer movie_db_id) {
        return watchlistManager.deleteFromWatchList(movie_db_id);
    }
}
