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

    @GetMapping
    public AllBookingInfoWrapper getAllReservations(@RequestHeader("userid") Long visitorId) {
        return reservationOrganizer.getReservationsWithExtraInfoFactory(visitorId);
    }

    @Transactional
    @PostMapping
    public boolean saveReservedSeats(@RequestBody SeatReservedWrapper reservationInfoWrapper, @RequestHeader("userid") Long visitorId) throws IllegalStateException {
        return reservationOrganizer.saveReservedSeats(reservationInfoWrapper, visitorId);
    }

    @DeleteMapping
    public boolean deleteReservation(@RequestBody SeatReservedWrapper seats, @RequestHeader("userid") Long visitorId) {
        return reservationOrganizer.deleteReservationWithRightsCheck(seats, visitorId);
    }

    // TODO: information must be combined in a service, rewrite
//    // TODO: Only for admin, user internally, user should reach only user's seats! Secure endpoint
    @GetMapping("/user")
    public ReservationWrapper getAllReservedSeats() {
        return reservationOrganizer.getAllReservations();
    }

    @GetMapping("/user/{id}")
    public ReservationWrapper getReservationsOfUser(@PathVariable("id") Long id) {
        return reservationOrganizer.getAllReservationsOfUserWithExtraInfo(id);
    }

    // TODO: limit visitor info to only logged in user!
    @GetMapping("/show/{id}")
    public AllBookingInfoWrapper getReservationsByShow(@PathVariable("id") Long showId) {
        return reservationOrganizer.getReservationsByShowId(showId);
    }

}
