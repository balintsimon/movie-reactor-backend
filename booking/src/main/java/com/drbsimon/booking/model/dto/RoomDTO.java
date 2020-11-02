package com.drbsimon.booking.model.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Long id;
    private String name;
    private Integer numberOfRows;
    private Integer numberOfSeatsPerRow;
    private List<SeatDTO> seats;
}
