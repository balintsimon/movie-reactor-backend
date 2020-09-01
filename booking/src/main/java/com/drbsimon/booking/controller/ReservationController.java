package com.drbsimon.booking.controller;

import com.drbsimon.booking.entity.SeatReservedWrapper;
import com.drbsimon.booking.repository.ReservationRepository;
import com.drbsimon.booking.service.ReservationOrganizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@CrossOrigin
@RestController
@RequestMapping("/reservation")
@Slf4j
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationOrganizer reservationOrganizer;

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
//    // TODO: Only for admin, user should reach only user's seats! Secure endpoint
//    public List<SeatReservedWithDetails> getAllReservedSeats() {
//        return reservationRepository.getAllReservationsWithDetails();
//    }

}
