package com.drbsimon.moviecatalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {
    private Long id;
    private Integer rowNumber;
    private Integer seatNumber;
    private RoomDTO room;
}
