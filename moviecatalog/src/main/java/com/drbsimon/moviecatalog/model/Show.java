package com.drbsimon.moviecatalog.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Show {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate startingDate;
    private LocalTime startingTime;

//    @ToString.Exclude
//    @JsonManagedReference
//    @EqualsAndHashCode.Exclude
//    @OneToMany(mappedBy = "show", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//    private List<SeatReserved> reservedSeats;

//    @ManyToOne
//    @EqualsAndHashCode.Exclude
//    @JsonManagedReference
    private Long movieId;
    private int movieDbId;

//    @ManyToOne
//    @JsonManagedReference
//    @EqualsAndHashCode.Exclude
    private Long roomId;
}
