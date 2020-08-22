package com.drbsimon.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Show {
    private Long id;
    private LocalDate startingDate;
    private LocalTime startingTime;
    private Long movieId;
    private Long roomId;
}
