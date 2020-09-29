package com.drbsimon.apigateway.controller;

import com.drbsimon.apigateway.model.dto.UserCredentialsDTO;
import com.drbsimon.apigateway.model.dto.VisitorLoginDTO;
import com.drbsimon.apigateway.model.dto.VisitorRegisterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "${main.route}")
@CrossOrigin
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final VisitorLoginDTO visitorLoginDTO;
    private final VisitorRegisterDTO visitorRegisterDTO;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        return visitorLoginDTO.loginUser(userCredentialsDTO);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        return visitorRegisterDTO.registerUser(userCredentialsDTO);
    }
}

