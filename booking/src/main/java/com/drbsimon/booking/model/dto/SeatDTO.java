package com.drbsimon.booking.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {
    private Long id;
    private Integer rowNumber;
    private Integer seatNumber;
}
