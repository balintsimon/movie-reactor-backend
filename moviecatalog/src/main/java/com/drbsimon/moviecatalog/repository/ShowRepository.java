package com.drbsimon.moviecatalog.repository;

import com.drbsimon.moviecatalog.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    Show getShowById(Long showId);

    List<Show> findAll();

    void deleteAllByMovieId(Long movieId);

    void deleteById(Long id);
}
