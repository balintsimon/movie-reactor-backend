package com.drbsimon.cinema.model;

import com.drbsimon.cinema.entity.Seat;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class SeatListWrapper {
    List<Seat> seats;
}
