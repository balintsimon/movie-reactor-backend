package com.drbsimon.apigateway.model.entity;

import com.drbsimon.apigateway.model.Gender;
import com.drbsimon.apigateway.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Gender gender;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<>();

    @ElementCollection
    private List<Integer> watchList = new ArrayList<>();
}

