package com.drbsimon.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private List<Role> roles = new ArrayList<>();
}
