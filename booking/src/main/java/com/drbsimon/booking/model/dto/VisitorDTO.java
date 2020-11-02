package com.drbsimon.booking.model.dto;

import com.drbsimon.booking.model.Gender;
import com.drbsimon.booking.model.Role;
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
    private Gender gender;
    private List<Role> roles = new ArrayList<>();
}
