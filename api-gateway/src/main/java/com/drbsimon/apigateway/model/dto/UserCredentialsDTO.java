package com.drbsimon.apigateway.model.dto;

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
}
