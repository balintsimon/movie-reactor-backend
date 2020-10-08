package com.drbsimon.cinema.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
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

    @Min(0)
    private Integer numberOfRows;

    @Min(0)
    private Integer numberOfSeatsPerRow;

    @Transient
    private Integer capacity;

    @PostLoad
    private void postLoad() {
        calculateCapacity();
    }

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
