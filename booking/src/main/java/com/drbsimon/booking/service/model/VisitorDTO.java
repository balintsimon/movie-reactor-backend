package com.drbsimon.booking.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorDTO {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private List<Role> roles = new ArrayList<>();
}
