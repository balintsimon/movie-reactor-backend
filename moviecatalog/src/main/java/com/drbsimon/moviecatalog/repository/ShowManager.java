package com.drbsimon.moviecatalog.repository;

import com.drbsimon.moviecatalog.entity.Show;
import com.drbsimon.moviecatalog.model.ShowListWrapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
@RequiredArgsConstructor
public class ShowManager {
    private final ShowRepository showRepository;

    public ShowListWrapper getAllShows() {
        ShowListWrapper showListWrapper = new ShowListWrapper();
        List<Show> shows = showRepository.findAll();
        showListWrapper.setShows(shows);
        return showListWrapper;
    }

    public ShowListWrapper getAllCurrentShows() {
        LocalDate dateToday = LocalDate.now();
        LocalDate dateWeekFromNow = LocalDate.now().plusDays(6L);
        ShowListWrapper showListWrapper = new ShowListWrapper();
        List<Show> shows = showRepository.findAll();
        List<Show> currentShows = shows.stream()
                .filter(show -> show.getStartingDate().isAfter(dateToday)
                        && show.getStartingDate().isBefore(dateWeekFromNow))
                .collect(Collectors.toList());
        showListWrapper.setShows(currentShows);
        return showListWrapper;
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
