package com.drbsimon.cinema.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Room {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private Integer numberOfRows;
    private Integer numberOfSeatsPerRow;

    @Transient
    private Integer capacity;

    public void calculateCapacity () {
        if (numberOfRows != null && numberOfSeatsPerRow != null) {
            this.capacity = numberOfRows * numberOfSeatsPerRow;
        }
    }

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "room", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Seat> seats;
}