package com.drbsimon.apigateway.model.dto;

import com.drbsimon.apigateway.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsDTO {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private Gender gender;
}
