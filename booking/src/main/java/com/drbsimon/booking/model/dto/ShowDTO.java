package com.drbsimon.booking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO {
    private Long id;
    private LocalDate startingDate;
    private LocalTime startingTime;
    private Long movieId;
    private int movieDbId;
    private Long roomId;
}
