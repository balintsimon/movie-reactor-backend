package com.drbsimon.booking.service;

import com.drbsimon.booking.model.Reservation;
import com.drbsimon.booking.model.dto.SeatReservedDTO;
import com.drbsimon.booking.model.Role;
import com.drbsimon.booking.model.dto.AllBookingInfoDTO;
import com.drbsimon.booking.model.dto.SeatDTO;
import com.drbsimon.booking.model.dto.ShowDTO;
import com.drbsimon.booking.model.dto.VisitorDTO;
import com.drbsimon.booking.model.wrapper.AllBookingInfoWrapper;
import com.drbsimon.booking.model.wrapper.ReservationWrapper;
import com.drbsimon.booking.service.caller.CatalogServiceCaller;
import com.drbsimon.booking.service.caller.CinemaServiceCaller;
import com.drbsimon.booking.service.caller.VisitorServiceCaller;
import com.drbsimon.booking.service.dao.ReservationDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationDao reservationDao;
    private final CatalogServiceCaller catalogServiceCaller;
    private final CinemaServiceCaller cinemaServiceCaller;
    private final VisitorServiceCaller visitorServiceCaller;

    public boolean saveReservedSeats(SeatReservedDTO reservationInfo, Long visitorId)
            throws IllegalStateException {
        Long showId = reservationInfo.getId();
        List<Long> seatIds = reservationInfo.getSeats();
        VisitorDTO visitor = visitorServiceCaller.getVisitorById(visitorId);

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

            Reservation foundSeat = reservationDao.getBySeatIdAndShowId(actualSeatId, showId);
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
            reservationDao.save(newReservation);
        }
        return true;
    }

    public boolean deleteReservationWithRightsCheck(SeatReservedDTO reservationInfo, long visitorId) {
        VisitorDTO visitor = visitorServiceCaller.getVisitorById(visitorId);
        if (visitor.getRoles().contains(Role.ROLE_ADMIN)
                || (visitor.getRoles().contains(Role.ROLE_USER) && visitor.getId() == visitorId))
            return deleteReservation(reservationInfo);
        return false;
    }

    public boolean deleteReservation(SeatReservedDTO reservationInfo) {
        Long seatId = reservationInfo.getSeats().size() == 1 ? reservationInfo.getSeats().get(0) : null;
        Long showId = reservationInfo.getId();
        Long visitorId = reservationInfo.getVisitorId();
        if (seatId != null) {
            Reservation reservation = reservationDao.getBySeatIdAndVisitorIdAndShowId(seatId, visitorId, showId);
            System.out.println(reservation);
            if (reservation != null) {
                reservationDao.deleteById(reservation.getId());
                return true;
            }
        }
        return false;
    }

    public ReservationWrapper getAllReservations() {
        return new ReservationWrapper(reservationDao.findAll());
    }

    public ReservationWrapper getAllReservationsOfUserWithExtraInfo(Long visitorId) {
        return new ReservationWrapper(reservationDao.getAllByVisitorId(visitorId));
    }

    public AllBookingInfoWrapper getReservationsWithExtraInfoFactory(Long visitorId) {
        VisitorDTO visitor = visitorServiceCaller.getVisitorById(visitorId);
        if (visitor.getRoles().contains(Role.ROLE_ADMIN)) return getAllReservationsWithExtraInfo();
        else if (visitor.getRoles().contains(Role.ROLE_USER)) return getAllReservationsOfUserWithExtraInfo(visitor);
        return null;
    }

    private AllBookingInfoWrapper getAllReservationsWithExtraInfo() {
        List<Reservation> reservations = reservationDao.findAll();
        List<AllBookingInfoDTO> allInformation = new ArrayList<>();
        Map<Long, VisitorDTO> visitors = new HashMap<>();
        Map<Long, ShowDTO> shows = new HashMap<>();
        Map<Long, SeatDTO> seats = new HashMap<>();
        for (Reservation reservation : reservations) {
            ShowDTO show;
            if (shows.containsKey(reservation.getShowId())) show = shows.get(reservation.getShowId());
            else {
                show = catalogServiceCaller.getShowById(reservation.getShowId());
                shows.put(reservation.getShowId(), show);
            }

            VisitorDTO visitor;
            if (visitors.containsKey(reservation.getVisitorId())) visitor = visitors.get(reservation.getVisitorId());
            else {
                visitor = visitorServiceCaller.getVisitorById(reservation.getVisitorId());
                visitors.put(reservation.getVisitorId(), visitor);
            }

            SeatDTO seat;
            if (seats.containsKey(reservation.getSeatId())) seat = seats.get(reservation.getSeatId());
            else {
                seat = cinemaServiceCaller.getSeatById(reservation.getSeatId());
                seats.put(reservation.getSeatId(), seat);
            }
            allInformation.add(new AllBookingInfoDTO(reservation.getId(), visitor, seat, show, show.getMovieDbId()));
        }
        return new AllBookingInfoWrapper(allInformation);
    }

    private AllBookingInfoWrapper getAllReservationsOfUserWithExtraInfo(VisitorDTO visitor) {
        List<Reservation> reservations = reservationDao.getAllByVisitorId(visitor.getId());
        List<AllBookingInfoDTO> allInformation = new ArrayList<>();
        Map<Long, ShowDTO> shows = new HashMap<>();
        Map<Long, SeatDTO> seats = new HashMap<>();
        for (Reservation reservation : reservations) {
            ShowDTO show;
            if (shows.containsKey(reservation.getShowId())) show = shows.get(reservation.getShowId());
            else {
                show = catalogServiceCaller.getShowById(reservation.getShowId());
                shows.put(reservation.getShowId(), show);
            }

            SeatDTO seat;
            if (seats.containsKey(reservation.getSeatId())) seat = seats.get(reservation.getSeatId());
            else {
                seat = cinemaServiceCaller.getSeatById(reservation.getSeatId());
                seats.put(reservation.getSeatId(), seat);
            }
            allInformation.add(new AllBookingInfoDTO(reservation.getId(), visitor, seat, show, show.getMovieDbId()));
        }
        return new AllBookingInfoWrapper(allInformation);
    }

    public AllBookingInfoWrapper getAllReservationsFactory(Long showId, Long visitorId) {
        if (visitorId != null) {
            VisitorDTO visitor = visitorServiceCaller.getVisitorById(visitorId);
            if (visitor.getRoles().contains(Role.ROLE_ADMIN)) return getAllReservationsWithDetailsByShowId(showId);
            else if (visitor.getRoles().contains(Role.ROLE_USER))
                return getAllReservationsWithDetailsOfLoggedInUserByShowId(showId, visitor);
        }
        return getReservationsByShowIdWithoutUserInformation(showId);
    }


    public AllBookingInfoWrapper getAllReservationsWithDetailsByShowId(Long showId) {
        List<Reservation> reservations = reservationDao.getAllByShowId(showId);
        List<AllBookingInfoDTO> allInformation = new ArrayList<>();
        Map<Long, VisitorDTO> visitors = new HashMap<>();
        Map<Long, ShowDTO> shows = new HashMap<>();
        Map<Long, SeatDTO> seats = new HashMap<>();
        for (Reservation reservation : reservations) {
            ShowDTO show;
            if (shows.containsKey(reservation.getShowId())) show = shows.get(reservation.getShowId());
            else {
                show = catalogServiceCaller.getShowById(reservation.getShowId());
                shows.put(reservation.getShowId(), show);
            }

            VisitorDTO visitor;
            if (visitors.containsKey(reservation.getVisitorId())) visitor = visitors.get(reservation.getVisitorId());
            else {
                visitor = visitorServiceCaller.getVisitorById(reservation.getVisitorId());
                visitors.put(reservation.getVisitorId(), visitor);
            }

            SeatDTO seat;
            if (seats.containsKey(reservation.getSeatId())) seat = seats.get(reservation.getSeatId());
            else {
                seat = cinemaServiceCaller.getSeatById(reservation.getSeatId());
                seats.put(reservation.getSeatId(), seat);
            }
            allInformation.add(new AllBookingInfoDTO(reservation.getId(), visitor, seat, show, show.getMovieDbId()));
        }
        return new AllBookingInfoWrapper(allInformation);
    }

    public AllBookingInfoWrapper getAllReservationsWithDetailsOfLoggedInUserByShowId(Long showId, VisitorDTO loggedInVisitor) {
        List<Reservation> reservations = reservationDao.getAllByShowId(showId);
        List<AllBookingInfoDTO> allInformation = new ArrayList<>();
        Map<Long, ShowDTO> shows = new HashMap<>();
        Map<Long, SeatDTO> seats = new HashMap<>();
        for (Reservation reservation : reservations) {
            ShowDTO show;
            if (shows.containsKey(reservation.getShowId())) show = shows.get(reservation.getShowId());
            else {
                show = catalogServiceCaller.getShowById(reservation.getShowId());
                shows.put(reservation.getShowId(), show);
            }

            VisitorDTO visitor = (reservation.getVisitorId().equals(loggedInVisitor.getId())) ? loggedInVisitor
                    : new VisitorDTO();

            SeatDTO seat;
            if (seats.containsKey(reservation.getSeatId())) seat = seats.get(reservation.getSeatId());
            else {
                seat = cinemaServiceCaller.getSeatById(reservation.getSeatId());
                seats.put(reservation.getSeatId(), seat);
            }
            allInformation.add(new AllBookingInfoDTO(reservation.getId(), visitor, seat, show, show.getMovieDbId()));
        }
        return new AllBookingInfoWrapper(allInformation);
    }


    public AllBookingInfoWrapper getReservationsByShowIdWithoutUserInformation(Long showId) {
        List<Reservation> reservations = reservationDao.getAllByShowId(showId);
        List<AllBookingInfoDTO> allInformation = new ArrayList<>();
        Map<Long, ShowDTO> shows = new HashMap<>();
        Map<Long, SeatDTO> seats = new HashMap<>();
        for (Reservation reservation : reservations) {
            ShowDTO show;
            if (shows.containsKey(reservation.getShowId())) show = shows.get(reservation.getShowId());
            else {
                show = catalogServiceCaller.getShowById(reservation.getShowId());
                shows.put(reservation.getShowId(), show);
            }

            VisitorDTO visitor = new VisitorDTO();

            SeatDTO seat;
            if (seats.containsKey(reservation.getSeatId())) seat = seats.get(reservation.getSeatId());
            else {
                seat = cinemaServiceCaller.getSeatById(reservation.getSeatId());
                seats.put(reservation.getSeatId(), seat);
            }
            allInformation.add(new AllBookingInfoDTO(reservation.getId(), visitor, seat, show, show.getMovieDbId()));
        }
        return new AllBookingInfoWrapper(allInformation);
    }
}
