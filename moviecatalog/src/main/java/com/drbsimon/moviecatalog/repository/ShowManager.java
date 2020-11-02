package com.drbsimon.moviecatalog.repository;

import com.drbsimon.moviecatalog.entity.Show;
import com.drbsimon.moviecatalog.model.dto.MovieDTO;
import com.drbsimon.moviecatalog.model.wrapper.MovieListWrapper;
import com.drbsimon.moviecatalog.model.wrapper.ShowListWrapper;
import com.drbsimon.moviecatalog.service.MovieServiceCaller;
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
public class ShowManager {
    private final ShowRepository showRepository;
    private final MovieServiceCaller movieServiceCaller;

    public ShowListWrapper getAllShows() {
        ShowListWrapper showListWrapper = new ShowListWrapper();
        List<Show> shows = showRepository.findAll();
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
        List<Show> shows = showRepository.findAll();
        List<Show> currentShows = shows.stream()
                .filter(show -> show.getStartingDate().isAfter(fromDate)
                        && show.getStartingDate().isBefore(untilDate))
                .collect(Collectors.toList());
        showListWrapper.setShows(currentShows);
        return showListWrapper;
    }

    public MovieListWrapper getAllMoviesOnShow() {
        List<Show> shows = showRepository.findAll();
        List<MovieDTO> movies = new ArrayList<>();
        for (Show show : shows) {
            MovieDTO movie = movieServiceCaller.getMovieByMovieId(show.getMovieId());
            movies.add(movie);
        }
        return new MovieListWrapper(movies);
    }

    public Show getShowById(Long showId) {
        return showRepository.getShowById(showId);
    }

    public void deleteShowById(Long showId) {
        showRepository.deleteById(showId);
    }

    public void deleteShowByMovieId(Long movieId) {
        showRepository.deleteAllByMovieId(movieId);
    }

}
