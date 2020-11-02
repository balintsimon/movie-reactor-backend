package com.drbsimon.moviecatalog.data_sample;

import com.drbsimon.moviecatalog.entity.Show;
import com.drbsimon.moviecatalog.model.dto.MovieDTO;
import com.drbsimon.moviecatalog.model.dto.RoomDTO;
import com.drbsimon.moviecatalog.repository.ShowRepository;
import com.drbsimon.moviecatalog.service.MovieServiceCaller;
import com.drbsimon.moviecatalog.service.RoomServiceCaller;
import org.springframework.stereotype.Component;

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
        List<MovieDTO> movies = movieServiceCaller.getAllMovies();
        List<RoomDTO> rooms = roomServiceCaller.getAllRooms();

        for (int i = 0; i < 7; i++) {
            LocalTime startingTime = LocalTime.of(12, 0);

            for (MovieDTO movie : movies) {
                Integer runtime = movie.getRuntime() != null ? movie.getRuntime() : 130;

                Show currentShow = Show.builder()
                        .movieId(movie.getId())
                        .movieDbId(movie.getMovieDbId())
                        .startingDate(fromDate)
                        .startingTime(startingTime)
                        .roomId(rooms.get(0).getId())
                        .build();
                showRepository.save(currentShow);
//                startingTime = startingTime.plusHours(2);
                startingTime = startingTime.plusMinutes(runtime + 10);
            }
            fromDate = fromDate.plusDays(1);
        }
    }
}
