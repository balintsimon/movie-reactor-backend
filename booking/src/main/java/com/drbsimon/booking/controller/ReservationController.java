package com.drbsimon.booking.controller;

import com.drbsimon.booking.model.dto.SeatReservedDTO;
import com.drbsimon.booking.model.wrapper.AllBookingInfoWrapper;
import com.drbsimon.booking.model.dto.MessageDTO;
import com.drbsimon.booking.model.wrapper.ReservationWrapper;
import com.drbsimon.booking.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

//@CrossOrigin
@RestController
@RequestMapping("/reservation")
@Slf4j
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    public AllBookingInfoWrapper getAllReservations(@RequestHeader("userid") Long visitorId) {
        return reservationService.getReservationsWithExtraInfoFactory(visitorId);
    }

    @Transactional
    @PostMapping
    public boolean saveReservedSeats(@RequestBody SeatReservedDTO reservationInfoWrapper, @RequestHeader("userid") Long visitorId) throws IllegalStateException {
        return reservationService.saveReservedSeats(reservationInfoWrapper, visitorId);
    }

    @DeleteMapping
    public ResponseEntity<MessageDTO> deleteReservation(@RequestBody SeatReservedDTO seats, @RequestHeader("userid") Long visitorId) {
        MessageDTO responseMessage = new MessageDTO();
        if (reservationService.deleteReservationWithRightsCheck(seats, visitorId)) {
            responseMessage.setSuccessful(true);
            responseMessage.setMessage("Reservation successfully deleted!");
        } else {
            responseMessage.setSuccessful(false);
            responseMessage.setMessage("Database error.");
        }
        return ResponseEntity.ok(responseMessage);
    }

    // TODO: Only for admin, user internally, user should reach only user's seats! Secure endpoint
    @GetMapping("/user")
    public ReservationWrapper getAllReservedSeats() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/user/{id}")
    public ReservationWrapper getReservationsOfUser(@PathVariable("id") Long id) {
        return reservationService.getAllReservationsOfUserWithExtraInfo(id);
    }

    // TODO: limit visitor info to only logged in user!
    @GetMapping("/show/{id}")
    public AllBookingInfoWrapper getReservationsByShow(@PathVariable("id") Long showId, @RequestHeader("userid") Long visitorId) {
        return reservationService.getAllReservationsFactory(showId, visitorId);
    }

}
