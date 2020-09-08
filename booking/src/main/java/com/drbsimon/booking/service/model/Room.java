package com.drbsimon.booking.service.model;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private Long id;
    private String name;
    private Integer numberOfRows;
    private Integer numberOfSeatsPerRow;
    private List<Seat> seats;
}
