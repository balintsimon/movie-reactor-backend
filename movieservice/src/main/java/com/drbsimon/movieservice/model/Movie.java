package com.drbsimon.movieservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Movie {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Integer movieDbId;

    private Integer runtime;
}
