package com.drbsimon.booking.repository;

import com.drbsimon.booking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAll();

    Reservation getById(Long id);

    List<Reservation> getReservedSeatsByShowId(Long showId);

    List<Reservation> getAllByVisitorId(Long visitorId);

    Reservation getBySeatIdAndShowId(Long seatId, Long showId);

    Reservation getBySeatIdAndVisitorIdAndShowId(Long seatId, Long visitorId, Long showId);

    List<Reservation> getAllByVisitorIdAndShowId(Long visitorId, Long showId);

    List<Reservation> getAllByShowId(Long showId);

    @Transactional
    void deleteById(Long id);
}
