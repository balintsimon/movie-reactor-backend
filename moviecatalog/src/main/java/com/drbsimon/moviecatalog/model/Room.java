package com.drbsimon.moviecatalog.model;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private Long id;
    private String name;
    private Integer numberOfRows;
    private Integer numberOfSeatsPerRow;
    private List<Seat> seats;
}
