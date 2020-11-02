package com.drbsimon.moviecatalog.model.wrapper;

import com.drbsimon.moviecatalog.model.dto.MovieDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieListWrapper {
    List<MovieDTO> movies;
}
