package com.drbsimon.moviecatalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    private Long id;
    private Integer rowNumber;
    private Integer seatNumber;
    private Room room;
}
