package com.drbsimon.booking.controller;

import com.drbsimon.booking.entity.SeatReservedWrapper;
import com.drbsimon.booking.model.AllBookingInfoWrapper;
import com.drbsimon.booking.model.ReservationWrapper;
import com.drbsimon.booking.repository.ReservationOrganizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

//@CrossOrigin
@RestController
@RequestMapping("/reservation")
@Slf4j
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationOrganizer reservationOrganizer;

    @GetMapping("/reservation")
    public AllBookingInfoWrapper getAllReservations() {
        return reservationOrganizer.getReservationsWithExtraInfoFactory();
    }

    @Transactional
//    @PostMapping("/seats") // TODO: rewrite frontend endpoint
    @PostMapping
    public boolean saveReservedSeats(@RequestBody SeatReservedWrapper reservationInfoWrapper) throws IllegalStateException {
        return reservationOrganizer.saveReservedSeats(reservationInfoWrapper);
    }

//    @DeleteMapping("/delete") // TODO: rewrite frontend endpoint
    @DeleteMapping
    public boolean deleteReservation(@RequestBody SeatReservedWrapper seats) {
        return reservationOrganizer.deleteReservation(seats);
    }

    // TODO: information must be combined in a service, rewrite
    // NB: doesn't work anymore, services are not linked!
//    @GetMapping("/seats")
//    // TODO: Only for admin, user internally, user should reach only user's seats! Secure endpoint
    @GetMapping("/user")
    public ReservationWrapper getAllReservedSeats() {
        return reservationOrganizer.getAllReservations();
    }

    @GetMapping("/user/{id}")
    public ReservationWrapper getReservationsOfUser(@PathVariable("id") Long id) {
        return reservationOrganizer.getAllReservationsOfUserWithExtraInfo(id);
    }

}
