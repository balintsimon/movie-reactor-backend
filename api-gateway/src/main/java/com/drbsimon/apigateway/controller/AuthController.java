package com.codecool.moviereactorapplication.pet.controller;

import com.drbsimon.apigateway.entity.Visitor;
import com.drbsimon.apigateway.model.Gender;
import com.drbsimon.apigateway.model.UserCredentials;
import com.codecool.moviereactorapplication.pet.repository.VisitorRepository;
import com.drbsimon.apigateway.security.DataValidatorService;
import com.drbsimon.apigateway.security.JwtTokenServices;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "${main.route}")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final VisitorRepository allVisitors;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenServices jwtTokenServices;
    private final DataValidatorService dataValidator;
    private final PasswordEncoder passwordEncoder;

    // TODO: rearrange to one line
    public AuthController(AuthenticationManager authenticationManager, JwtTokenServices jwtTokenServices, VisitorRepository visitors, VisitorRepository allVisitors, DataValidatorService dataValidator) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenServices = jwtTokenServices;
        this.allVisitors = allVisitors;
        this.dataValidator = dataValidator;
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // TODO: rearrange to one line
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserCredentials userCredentials) {
        Map<Object, Object> model = new HashMap<>();
        try {
            String username = userCredentials.getUsername();
            String password = userCredentials.getPassword();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            String token = jwtTokenServices.createToken(username, roles);
            String gender = allVisitors.getGenderByUsername(username).getGender().toString();
            model.put("correct", true);
            model.put("username", username);
            model.put("roles", roles);
            model.put("token", token);
            model.put("gender", gender);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            model.put("correct", false);
            model.put("msg", "Username or/and password is not correct!");
            return ResponseEntity.ok(model);
        }
    }

    // TODO: rearrange to one line
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserCredentials userCredentials) {
        String username = userCredentials.getUsername();
        String password = userCredentials.getPassword();
        String firstname = userCredentials.getFirstname();
        String lastname = userCredentials.getLastname();
        String email = userCredentials.getEmail();
        Gender gender = userCredentials.getGender();

        Map<Object, Object> model = new HashMap<>();
        List<String> errorList = new ArrayList<>();

        if (allVisitors.findByUsername(username).isPresent()) {
            model.put("correct", false);
            model.put("msg", "Username already exists! Please choose a different username!");
            return ResponseEntity.ok(model);
        }
        if (!dataValidator.isValidUsername(username, errorList)) {
            String error = String.join(" , ", errorList);
            String errorMessage = "Username should have " + error + "!";
            model.put("correct", false);
            model.put("msg", errorMessage);
            return ResponseEntity.ok(model);
        }

        if (!dataValidator.isValidPassword(password, errorList)) {
            String error = String.join(" , ", errorList);
            String errorMessage = "Password should contain " + error + "!";
            model.put("correct", false);
            model.put("msg", errorMessage);
            return ResponseEntity.ok(model);
        }

        if (!dataValidator.isValidName(firstname, errorList)) {
            String error = String.join(" , ", errorList);
            String errorMessage = "Firstname " + error + "!";
            model.put("correct", false);
            model.put("msg", errorMessage);
            return ResponseEntity.ok(model);
        }

        if (!dataValidator.isValidName(lastname, errorList)) {
            String error = String.join(" , ", errorList);
            String errorMessage = "Lastname " + error + "!";
            model.put("correct", false);
            model.put("msg", errorMessage);
            return ResponseEntity.ok(model);
        }

        if (!dataValidator.isValidEmail(email, errorList)) {
            model.put("correct", false);
            model.put("msg", "Not valid email!");
            return ResponseEntity.ok(model);
        }

        if (!dataValidator.isValidGender(gender, errorList)) {
            model.put("correct", false);
            model.put("msg", "Not related data!");
            return ResponseEntity.ok(model);
        }

        Visitor newVisitor = Visitor.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .gender(gender)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        allVisitors.save(newVisitor);

        List<String> roles = Collections.singletonList("ROLE_USER");
        String token = jwtTokenServices.createToken(username, roles);
        model.put("correct", true);
        model.put("username", username);
        model.put("roles", roles);
        model.put("token", token);
        return ResponseEntity.ok(model);
    }
}

