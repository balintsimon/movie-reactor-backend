package com.drbsimon.apigateway.controller;

import com.drbsimon.apigateway.model.UserCredentials;
import com.drbsimon.apigateway.model.VisitorLoginService;
import com.drbsimon.apigateway.model.VisitorRegisterService;
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
    private final VisitorLoginService visitorLoginService;
    private final VisitorRegisterService visitorRegisterService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserCredentials userCredentials) {
        return visitorLoginService.loginUser(userCredentials);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserCredentials userCredentials) {
        return visitorRegisterService.registerUser(userCredentials);
    }
}

