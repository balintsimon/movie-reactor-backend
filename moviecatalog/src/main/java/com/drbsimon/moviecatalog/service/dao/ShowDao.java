package com.drbsimon.moviecatalog.service.dao;

import com.drbsimon.moviecatalog.model.Show;

import java.util.List;

public interface ShowDao {
    Show getShowById(Long showId);
    List<Show> findAll();
    void deleteAllByMovieId(Long movieId);
    void deleteById(Long id);
    void save(Show show);
}
