package com.drbsimon.booking.service;

import com.drbsimon.booking.entity.Reservation;
import com.drbsimon.booking.entity.SeatReservedWrapper;
import com.drbsimon.booking.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationOrganizer {
    private final ReservationRepository reservationRepository;
    private final CatalogServiceCaller catalogServiceCaller;
    private final CinemaServiceCaller cinemaServiceCaller;

    public boolean saveReservedSeats(SeatReservedWrapper reservationInfo)
            throws IllegalStateException {
        Long showId = reservationInfo.getId();
        List<Long> seatIds = reservationInfo.getSeats();

        if (seatIds.size() == 0) {
            return false;
        }

        if (!catalogServiceCaller.isShowValid(showId)) {
            throw new IllegalStateException("Show not found.");
        }

        for (Long actualSeatId : seatIds) {
            if (!cinemaServiceCaller.isSeatValid(actualSeatId)) {
                throw new IllegalStateException("Seat not found.");
            }

            Reservation foundSeat = reservationRepository.getBySeatIdAndShowId(actualSeatId, showId);
            if (foundSeat != null) {
                return false;
                // NB: throwing exception crashes frontend unbeknownst to the user.
                // It would be a bad user experience
//                    throw new IllegalStateException("Seat has been already taken.");
            }
        }

        for (Long actualSeatId : seatIds) {
            Reservation newReservation = Reservation.builder()
                    .seatId(actualSeatId)
                    .showId(showId)
                    .visitorId(reservationInfo.getVisitorId())
                    .build();
            reservationRepository.save(newReservation);
        }
        return true;
    }

    public boolean deleteReservation(SeatReservedWrapper reservationInfo) {
        Long seatId = reservationInfo.getSeats().size() == 1 ? reservationInfo.getSeats().get(0) : null;
        Long visitorId = reservationInfo.getVisitorId();
        if (seatId != null) {
            Reservation reservation = reservationRepository.getBySeatIdAndVisitorId(seatId, visitorId);
            if (reservation != null) {
                reservationRepository.deleteById(seatId);
                return true;
            }
        }
        return false;
    }
}
