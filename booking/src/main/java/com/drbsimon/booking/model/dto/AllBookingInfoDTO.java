package com.drbsimon.booking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllBookingInfoDTO {
    Long id;
    VisitorDTO visitor;
    SeatDTO seat;
    ShowDTO show;
    int movieId;
}
