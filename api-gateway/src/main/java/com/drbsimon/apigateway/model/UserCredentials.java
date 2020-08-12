package com.drbsimon.apigateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private Gender gender;
}
