package com.drbsimon.moviecatalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Long id;
    private String name;
    private Integer numberOfRows;
    private Integer numberOfSeatsPerRow;
    private Integer capacity;
    private List<SeatDTO> seats;
}
