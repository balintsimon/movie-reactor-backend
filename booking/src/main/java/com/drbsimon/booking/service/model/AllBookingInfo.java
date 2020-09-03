package com.drbsimon.booking.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllBookingInfo {
    Long id;
    Visitor visitor;
    Seat seat;
    Show show;
    int movieId;
}
