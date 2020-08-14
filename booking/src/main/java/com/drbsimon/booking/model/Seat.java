package com.drbsimon.booking.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    private Long id;
    private Integer rowNumber;
    private Integer seatNumber;
}
