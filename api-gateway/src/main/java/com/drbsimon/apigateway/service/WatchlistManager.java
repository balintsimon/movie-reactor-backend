package com.drbsimon.apigateway.repository;

import com.drbsimon.apigateway.model.entity.Visitor;
import com.drbsimon.apigateway.model.dto.WatchListDTO;
import com.drbsimon.apigateway.security.CustomUserDetailsService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@RequiredArgsConstructor
public class WatchlistManager {
    private final VisitorRepository visitorRepository;
    private final VisitorManager visitorManager;
    private final CustomUserDetailsService customUserDetailsService;

    public WatchListDTO getWatchlistByUsername() {
        Visitor user = customUserDetailsService.getVisitorFromToken();
        if (user == null) return new WatchListDTO(new ArrayList<>());
        List<Integer> watchlistIds = user.getWatchList();
        return new WatchListDTO(watchlistIds);
    }

    public boolean saveNewWatchlistElement(Integer movie_db_id) {
        Visitor visitor = customUserDetailsService.getVisitorFromToken();
        List<Integer> watchList = visitor.getWatchList();
        if (watchList.contains(movie_db_id)) return false;

        watchList.add(movie_db_id);
        visitor.setWatchList(watchList);
        visitorRepository.save(visitor);
        return true;
    }

    public boolean deleteMovieFromWatchListById(Integer movie_db_id) {
        Visitor visitor = customUserDetailsService.getVisitorFromToken();
        List<Integer> watchList = visitor.getWatchList();
        if (!watchList.contains(movie_db_id)) return false;

        watchList.remove(movie_db_id);
        visitor.setWatchList(watchList);
        visitorRepository.save(visitor);
        return true;
    }
}
