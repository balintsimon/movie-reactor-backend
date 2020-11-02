package com.drbsimon.moviecatalog.service;

import com.drbsimon.moviecatalog.model.Show;
import com.drbsimon.moviecatalog.model.dto.MovieDTO;
import com.drbsimon.moviecatalog.model.wrapper.MovieListWrapper;
import com.drbsimon.moviecatalog.model.wrapper.ShowListWrapper;
import com.drbsimon.moviecatalog.service.caller.MovieServiceCaller;
import com.drbsimon.moviecatalog.service.dao.ShowDao;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
@RequiredArgsConstructor
public class ShowService {
    private final ShowDao showDao;
    private final MovieServiceCaller movieServiceCaller;

    public ShowListWrapper getAllShows() {
        ShowListWrapper showListWrapper = new ShowListWrapper();
        List<Show> shows = showDao.findAll();
        showListWrapper.setShows(shows);
        return showListWrapper;
    }

    public ShowListWrapper getShowsForAWeekFromNow() {
        LocalDate today = LocalDate.now();
        LocalDate aWeekFromNow = LocalDate.now().plusDays(6L);
        return getAllCurrentShows(today, aWeekFromNow);
    }

    public ShowListWrapper getAllCurrentShows(LocalDate startDate, LocalDate endDate) {
        LocalDate fromDate = startDate.minusDays(1L);
        LocalDate untilDate = endDate.plusDays(1L);
        ShowListWrapper showListWrapper = new ShowListWrapper();
        List<Show> shows = showDao.findAll();
        List<Show> currentShows = shows.stream()
                .filter(show -> show.getStartingDate().isAfter(fromDate)
                        && show.getStartingDate().isBefore(untilDate))
                .collect(Collectors.toList());
        showListWrapper.setShows(currentShows);
        return showListWrapper;
    }

    public MovieListWrapper getAllMoviesOnShow() {
        List<Show> shows = showDao.findAll();
        List<MovieDTO> movies = new ArrayList<>();
        for (Show show : shows) {
            MovieDTO movie = movieServiceCaller.getMovieByMovieId(show.getMovieId());
            movies.add(movie);
        }
        return new MovieListWrapper(movies);
    }

    public Show getShowById(Long showId) {
        return showDao.getShowById(showId);
    }

    public void deleteShowById(Long showId) {
        showDao.deleteById(showId);
    }

    public void deleteShowByMovieId(Long movieId) {
        showDao.deleteAllByMovieId(movieId);
    }

}
