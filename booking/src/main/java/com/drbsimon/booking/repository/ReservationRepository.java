package com.drbsimon.booking.repository;

import com.drbsimon.booking.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> getAll();

    Reservation getById(Long id);

    List<Reservation> getReservedSeatsByShowId(Long showId);

    List<Reservation> getAllByVisitorId(Long visitorId);

    Reservation getBySeatIdAndShowId(Long seatId, Long showId);

    Reservation getBySeatIdAndVisitorId(Long seatId, Long visitorId);

    List<Reservation> getAllByVisitorIdAndShowId(Long visitorId, Long showId);

    @Transactional
    void deleteById(Long Id);
}
