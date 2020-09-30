package com.drbsimon.apigateway.controller;

import com.drbsimon.apigateway.model.dto.UserCredentialsDTO;
import com.drbsimon.apigateway.model.dto.VisitorLoginDTO;
import com.drbsimon.apigateway.model.dto.VisitorRegisterDTO;
import com.drbsimon.apigateway.repository.VisitorRepository;
import com.drbsimon.apigateway.security.CustomUserDetailsService;
import com.drbsimon.apigateway.security.DataValidatorService;
import com.drbsimon.apigateway.security.JwtTokenServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = VisitorLoginDTO.class)
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private VisitorLoginDTO visitorLoginDTO;

//    @Autowired
//    private MockMvc mockMvc;

    // Dependencies necessary to start tests
    @MockBean
    private ZuulProperties zuulProperties;

    @MockBean
    private VisitorRepository repository;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtTokenServices jwtTokenServices;

    @MockBean
    private DataValidatorService dataValidator;

    // Further mocking
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void textContextLoaded() {
        assertThat(visitorLoginDTO).isNotNull();
    }

}