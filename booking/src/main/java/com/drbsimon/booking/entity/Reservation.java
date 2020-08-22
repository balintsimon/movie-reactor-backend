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
//@Table(uniqueConstraints={
//        @UniqueConstraint(columnNames = {"seat_id", "show_id"}) // TODO: Check if this actually works
//})
/*
* Unable to create unique key constraint (seat_id, show_id)
* on table reservation:
* database column 'show_id', 'seat_id' not found.
* Make sure that you use the correct column name
* which depends on the naming strategy in use
* (it may not be the same as the property name in the entity, especially for relational types)
* */
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long seatId;

    @Column(nullable = false)
    private Long showId;

    @Column(nullable = false)
    private Long visitorId;
}
