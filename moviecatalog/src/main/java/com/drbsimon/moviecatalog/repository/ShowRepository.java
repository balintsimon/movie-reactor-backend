package com.drbsimon.moviecatalog.repository;

import com.drbsimon.moviecatalog.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    Show getShowById(Long showId);

    List<Show> findAll();

    @Transactional
    void deleteAllByMovieId(Long movieId);

    @Transactional
    void deleteById(Long id);
}
