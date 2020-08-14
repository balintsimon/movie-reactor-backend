package com.drbsimon.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class SeatReservedWithDetails {
    // TODO: needed for admin on frontend only; should be reworked
    // TODO: check frontend information - rewrite to send only necessary data

    private Long id; // which id is this??? check frontend
    private Long visitorId;
    private Long showId;
    private LocalDate startingDate;
    private LocalTime startingTime;
    private Integer movieDbId; // TODO: rewrite frontend to get movieID instead movieDbId
                               // OR integrate movie service to give movie id by movieDbId
    private Integer rowNumber;
    private Integer seatNumber;

}
