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
@Table(uniqueConstraints=@UniqueConstraint(columnNames = {"seat_id", "show_id"}))
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "seat_id")
    private Long seatId;

    @Column(nullable = false, name = "show_id")
    private Long showId;

    @Column(nullable = false)
    private Long visitorId;
}
