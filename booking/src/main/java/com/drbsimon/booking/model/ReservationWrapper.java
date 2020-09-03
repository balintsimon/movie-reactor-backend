package com.drbsimon.apigateway.wrapper;

import com.drbsimon.apigateway.service.model.Reservation;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class ReservationWrapper {
    List<Reservation> reservations;
}
