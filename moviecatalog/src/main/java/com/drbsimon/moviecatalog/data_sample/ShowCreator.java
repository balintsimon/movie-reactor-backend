package com.drbsimon.moviecatalog.data_sample;

import com.drbsimon.moviecatalog.entity.Show;
import com.drbsimon.moviecatalog.model.Movie;
import com.drbsimon.moviecatalog.model.Room;
import com.drbsimon.moviecatalog.repository.ShowRepository;
import com.drbsimon.moviecatalog.service.MovieServiceCaller;
import com.drbsimon.moviecatalog.service.RoomServiceCaller;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class ShowCreator {
    private final ShowRepository showRepository;
    private final MovieServiceCaller movieServiceCaller;
    private final RoomServiceCaller roomServiceCaller;

    public ShowCreator(ShowRepository showRepository, MovieServiceCaller movieServiceCaller, RoomServiceCaller roomServiceCaller) {
        this.showRepository = showRepository;
        this.movieServiceCaller = movieServiceCaller;
        this.roomServiceCaller = roomServiceCaller;
    }

    public void createWeeklyScheduleData(LocalDate fromDate) {
        List<Movie> movies = movieServiceCaller.getAllMovies();
        List<Room> rooms = roomServiceCaller.getAllRooms();

        for (int i = 0; i < 7; i++) {
            LocalTime startingTime = LocalTime.of(12, 0);

            for (Movie movie : movies) {
                Show currentShow = Show.builder()
                        .movieId(movie.getId())
                        .movieDbId(movie.getMovieDbId())
                        .startingDate(fromDate)
                        .startingTime(startingTime)
                        .roomId(rooms.get(0).getId())
                        .build();
                showRepository.save(currentShow);
                startingTime = startingTime.plusHours(2);
            }
            fromDate = fromDate.plusDays(1);
        }
    }
}
