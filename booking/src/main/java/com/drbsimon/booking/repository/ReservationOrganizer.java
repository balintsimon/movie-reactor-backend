package com.drbsimon.booking.repository;

import com.drbsimon.booking.entity.Reservation;
import com.drbsimon.booking.entity.SeatReservedWrapper;
import com.drbsimon.booking.model.AllBookingInfoWrapper;
import com.drbsimon.booking.model.ReservationWrapper;
import com.drbsimon.booking.service.caller.CatalogServiceCaller;
import com.drbsimon.booking.service.caller.CinemaServiceCaller;
import com.drbsimon.booking.service.caller.VisitorServiceCaller;
import com.drbsimon.booking.service.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ReservationOrganizer {
    private final ReservationRepository reservationRepository;
    private final CatalogServiceCaller catalogServiceCaller;
    private final CinemaServiceCaller cinemaServiceCaller;
    private final VisitorServiceCaller visitorServiceCaller;

    public boolean saveReservedSeats(SeatReservedWrapper reservationInfo, Long visitorId)
            throws IllegalStateException {
        Long showId = reservationInfo.getId();
        List<Long> seatIds = reservationInfo.getSeats();
        Visitor visitor = visitorServiceCaller.getVisitorById(visitorId);

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
                    .visitorId(visitor.getId())
                    .build();
            reservationRepository.save(newReservation);
        }
        return true;
    }

    public boolean deleteReservationWithRightsCheck(SeatReservedWrapper reservationInfo, long visitorId) {
        Visitor visitor = visitorServiceCaller.getVisitorById(visitorId);
        if (visitor.getRoles().contains(Role.ROLE_ADMIN)
                ||  (visitor.getRoles().contains(Role.ROLE_USER) && visitor.getId() == visitorId))
            return deleteReservation(reservationInfo);
        return false;
    }

    public boolean deleteReservation(SeatReservedWrapper reservationInfo) {
        Long seatId = reservationInfo.getSeats().size() == 1 ? reservationInfo.getSeats().get(0) : null;
        Long showId = reservationInfo.getId();
        Long visitorId = reservationInfo.getVisitorId();
        if (seatId != null) {
            Reservation reservation = reservationRepository.getBySeatIdAndVisitorIdAndShowId(seatId, visitorId, showId);
            System.out.println(reservation);
            if (reservation != null) {
                reservationRepository.deleteById(reservation.getId());
                return true;
            }
        }
        return false;
    }

    public ReservationWrapper getAllReservations() {
        return new ReservationWrapper(reservationRepository.findAll());
    }

    public ReservationWrapper getAllReservationsOfUserWithExtraInfo(Long visitorId) {
        return new ReservationWrapper(reservationRepository.getAllByVisitorId(visitorId));
    }

    public AllBookingInfoWrapper getReservationsWithExtraInfoFactory(Long visitorId) {
        Visitor visitor = visitorServiceCaller.getVisitorById(visitorId);
        if (visitor.getRoles().contains(Role.ROLE_ADMIN)) return getAllReservationsWithExtraInfo();
        else if (visitor.getRoles().contains(Role.ROLE_USER)) return getAllReservationsOfUserWithExtraInfo(visitor);
        return null;
    }

    private AllBookingInfoWrapper getAllReservationsWithExtraInfo() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<AllBookingInfo> allInformation = new ArrayList<>();
        Map<Long, Visitor> visitors = new HashMap<>();
        Map<Long, Show> shows = new HashMap<>();
        Map<Long, Seat> seats = new HashMap<>();
        for (Reservation reservation : reservations) {
            Show show;
            if (shows.containsKey(reservation.getShowId())) show = shows.get(reservation.getShowId());
            else {
                show = catalogServiceCaller.getShowById(reservation.getShowId());
                shows.put(reservation.getShowId(), show);
            }

            Visitor visitor;
            if (visitors.containsKey(reservation.getVisitorId())) visitor = visitors.get(reservation.getVisitorId());
            else {
                visitor = visitorServiceCaller.getVisitorById(reservation.getVisitorId());
                visitors.put(reservation.getVisitorId(), visitor);
            }

            Seat seat;
            if (seats.containsKey(reservation.getSeatId())) seat = seats.get(reservation.getSeatId());
            else {
                seat = cinemaServiceCaller.getSeatById(reservation.getSeatId());
                seats.put(reservation.getSeatId(), seat);
            }
            allInformation.add(new AllBookingInfo(reservation.getId(), visitor, seat, show, show.getMovieDbId()));
        }
        return new AllBookingInfoWrapper(allInformation);
    }

    private AllBookingInfoWrapper getAllReservationsOfUserWithExtraInfo(Visitor visitor) {
        List<Reservation> reservations = reservationRepository.getAllByVisitorId(visitor.getId());
        List<AllBookingInfo> allInformation = new ArrayList<>();
        Map<Long, Show> shows = new HashMap<>();
        Map<Long, Seat> seats = new HashMap<>();
        for (Reservation reservation : reservations) {
            Show show;
            if (shows.containsKey(reservation.getShowId())) show = shows.get(reservation.getShowId());
            else {
                show = catalogServiceCaller.getShowById(reservation.getShowId());
                shows.put(reservation.getShowId(), show);
            }

            Seat seat;
            if (seats.containsKey(reservation.getSeatId())) seat = seats.get(reservation.getSeatId());
            else {
                seat = cinemaServiceCaller.getSeatById(reservation.getSeatId());
                seats.put(reservation.getSeatId(), seat);
            }
            allInformation.add(new AllBookingInfo(reservation.getId(), visitor, seat, show, show.getMovieDbId()));
        }
        return new AllBookingInfoWrapper(allInformation);
    }

    public AllBookingInfoWrapper getAllReservationsFactory(Long showId, Long visitorId) {
        if (visitorId != null) {
            Visitor visitor = visitorServiceCaller.getVisitorById(visitorId);
            if (visitor.getRoles().contains(Role.ROLE_ADMIN)) return getAllReservationsWithDetailsByShowId(showId);
            else if (visitor.getRoles().contains(Role.ROLE_USER)) return getReservationsByShowIdWithoutUserInformation(showId);
        }
        return getReservationsByShowIdWithoutUserInformation(showId);
    }


    public AllBookingInfoWrapper getAllReservationsWithDetailsByShowId(Long showId) {
        List<Reservation> reservations = reservationRepository.getAllByShowId(showId);
        List<AllBookingInfo> allInformation = new ArrayList<>();
        Map<Long, Visitor> visitors = new HashMap<>();
        Map<Long, Show> shows = new HashMap<>();
        Map<Long, Seat> seats = new HashMap<>();
        for (Reservation reservation : reservations) {
            Show show;
            if (shows.containsKey(reservation.getShowId())) show = shows.get(reservation.getShowId());
            else {
                show = catalogServiceCaller.getShowById(reservation.getShowId());
                shows.put(reservation.getShowId(), show);
            }

            Visitor visitor;
            if (visitors.containsKey(reservation.getVisitorId())) visitor = visitors.get(reservation.getVisitorId());
            else {
                visitor = visitorServiceCaller.getVisitorById(reservation.getVisitorId());
                visitors.put(reservation.getVisitorId(), visitor);
            }

            Seat seat;
            if (seats.containsKey(reservation.getSeatId())) seat = seats.get(reservation.getSeatId());
            else {
                seat = cinemaServiceCaller.getSeatById(reservation.getSeatId());
                seats.put(reservation.getSeatId(), seat);
            }
            allInformation.add(new AllBookingInfo(reservation.getId(), visitor, seat, show, show.getMovieDbId()));
        }
        return new AllBookingInfoWrapper(allInformation);
    }


    public AllBookingInfoWrapper getReservationsByShowIdWithoutUserInformation(Long showId) {
        List<Reservation> reservations = reservationRepository.getAllByShowId(showId);
        List<AllBookingInfo> allInformation = new ArrayList<>();
        Map<Long, Show> shows = new HashMap<>();
        Map<Long, Seat> seats = new HashMap<>();
        for (Reservation reservation : reservations) {
            Show show;
            if (shows.containsKey(reservation.getShowId())) show = shows.get(reservation.getShowId());
            else {
                show = catalogServiceCaller.getShowById(reservation.getShowId());
                shows.put(reservation.getShowId(), show);
            }

            Visitor visitor = new Visitor();

            Seat seat;
            if (seats.containsKey(reservation.getSeatId())) seat = seats.get(reservation.getSeatId());
            else {
                seat = cinemaServiceCaller.getSeatById(reservation.getSeatId());
                seats.put(reservation.getSeatId(), seat);
            }
            allInformation.add(new AllBookingInfo(reservation.getId(), visitor, seat, show, show.getMovieDbId()));
        }
        return new AllBookingInfoWrapper(allInformation);
    }
}
