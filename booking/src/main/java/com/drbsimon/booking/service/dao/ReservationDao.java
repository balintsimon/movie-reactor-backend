package com.drbsimon.booking.service.dao;

import com.drbsimon.booking.model.Reservation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public interface ReservationDao {
    List<Reservation> findAll();
    List<Reservation> getAllByVisitorId(Long visitorId);
    Reservation getBySeatIdAndShowId(Long seatId, Long showId);
    Reservation getBySeatIdAndVisitorIdAndShowId(Long seatId, Long visitorId, Long showId);
    List<Reservation> getAllByShowId(Long showId);
    void deleteById(Long id);
    void save(Reservation reservation);
}
