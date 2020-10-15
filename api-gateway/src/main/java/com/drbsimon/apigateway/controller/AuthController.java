package com.drbsimon.apigateway.controller;

import com.drbsimon.apigateway.model.dto.UserCredentialsDTO;
import com.drbsimon.apigateway.security.service.AuthService;
import com.drbsimon.apigateway.model.dto.VisitorRegisterDTO;
import com.drbsimon.apigateway.security.JwtTokenServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final VisitorRegisterDTO visitorRegister;
    private final JwtTokenServices jwtService;

    @PostMapping(value = "login", consumes = "application/json")
    public ResponseEntity login(@RequestBody UserCredentialsDTO loginDTO, HttpServletResponse response) {
        log.info("Login request received: " + loginDTO.toString());
        return authService.loginUser(loginDTO, response);
    }

    // TODO: move registration to authService!
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCredentialsDTO userCredentials) {
        log.info("Registration request received: " + userCredentials.toString());
        return visitorRegister.registerUser(userCredentials);
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        log.info("Logging out.");
        authService.logout(response);
    }

    @GetMapping("/isloggedin")
    public ResponseEntity<?> me(@CookieValue(value = "JWT", required = false) String jwt) {
        log.info("Check if am I logged in with cookie " + jwt);
        return ResponseEntity.ok().body(jwt != null && jwtService.validateToken(jwt));
    }
}

