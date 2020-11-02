package com.drbsimon.apigateway.service;

import com.drbsimon.apigateway.model.entity.Visitor;
import com.drbsimon.apigateway.model.dto.WatchListDTO;
import com.drbsimon.apigateway.security.CustomUserDetailsService;
import com.drbsimon.apigateway.service.dao.VisitorServiceDao;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@RequiredArgsConstructor
public class WatchlistManager {
    private final VisitorServiceDao visitorServiceDao;
    private final CustomUserDetailsService customUserDetailsService;

    public WatchListDTO getVisitorWatchlist() {
        Visitor user = customUserDetailsService.parseVisitorFromToken();
        if (user == null) return new WatchListDTO(new ArrayList<>());
        List<Integer> watchlistIds = user.getWatchList();
        return new WatchListDTO(watchlistIds);
    }

    public boolean addToWatchList(Integer movie_db_id) {
        Visitor visitor = customUserDetailsService.parseVisitorFromToken();
        List<Integer> watchList = visitor.getWatchList();
        if (watchList.contains(movie_db_id)) return false;

        watchList.add(movie_db_id);
        visitor.setWatchList(watchList);
        visitorServiceDao.save(visitor);
        return true;
    }

    public boolean deleteFromWatchList(Integer movie_db_id) {
        Visitor visitor = customUserDetailsService.parseVisitorFromToken();
        List<Integer> watchList = visitor.getWatchList();
        if (!watchList.contains(movie_db_id)) return false;

        watchList.remove(movie_db_id);
        visitor.setWatchList(watchList);
        visitorServiceDao.save(visitor);
        return true;
    }
}
