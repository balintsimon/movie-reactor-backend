package com.drbsimon.booking.model.wrapper;
import com.drbsimon.booking.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationWrapper {
    List<Reservation> reservations;
}
