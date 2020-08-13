package com.drbsimon.moviecatalog.repository;

import com.drbsimon.moviecatalog.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    Show getShowById(Long showId);

    List<Show> findAll();
}
