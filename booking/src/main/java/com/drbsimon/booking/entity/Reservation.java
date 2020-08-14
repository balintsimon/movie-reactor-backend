package com.drbsimon.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// TODO: check if possible to give unique constraint for the combination of seat+show
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"seat_id", "show_id"}) // TODO: Check if this actually works
})
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private Long seatId;

    @Column(unique = true, nullable = false)
    private Long showId;

    @Column(unique = true, nullable = false)
    private Long visitorId;
}
