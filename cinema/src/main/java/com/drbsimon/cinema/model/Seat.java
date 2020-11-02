package com.drbsimon.cinema.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Seat {
    @Id
    @GeneratedValue
    private Long id;

    @Min(0)
    private Integer rowNumber;

    @Min(0)
    private Integer seatNumber;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Room room;
}
