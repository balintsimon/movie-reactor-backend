package com.drbsimon.booking.service.dao;

import com.drbsimon.booking.model.Reservation;
import com.drbsimon.booking.repository.ReservationRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class ReservationDaoDB implements ReservationDao {
    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getAllByVisitorId(Long visitorId) {
        return reservationRepository.getAllByVisitorId(visitorId);
    }

    @Override
    public Reservation getBySeatIdAndShowId(Long seatId, Long showId) {
        return reservationRepository.getBySeatIdAndShowId(seatId, showId);
    }

    @Override
    public Reservation getBySeatIdAndVisitorIdAndShowId(Long seatId, Long visitorId, Long showId) {
        return reservationRepository.getBySeatIdAndVisitorIdAndShowId(seatId, visitorId, showId);
    }

    @Override
    public List<Reservation> getAllByShowId(Long showId) {
        return reservationRepository.getAllByShowId(showId);
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
    }
}
