package com.drbsimon.apigateway.controller;

import com.drbsimon.apigateway.entity.Visitor;
import com.drbsimon.apigateway.model.Gender;
import com.drbsimon.apigateway.model.UserCredentials;
import com.drbsimon.apigateway.model.VisitorLoginService;
import com.drbsimon.apigateway.repository.VisitorRepository;
import com.drbsimon.apigateway.security.DataValidatorService;
import com.drbsimon.apigateway.security.JwtTokenServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "${main.route}")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final VisitorRepository visitorRepository;
    private final JwtTokenServices jwtTokenServices;
    private final DataValidatorService dataValidator;
    private final PasswordEncoder passwordEncoder;
    private final VisitorLoginService visitorLoginService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserCredentials userCredentials) {
        return visitorLoginService.loginUser(userCredentials);
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

        if (visitorRepository.findByUsername(username).isPresent()) {
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
        visitorRepository.save(newVisitor);

        List<String> roles = Collections.singletonList("ROLE_USER");
        String token = jwtTokenServices.createToken(username, roles);
        model.put("correct", true);
        model.put("username", username);
        model.put("roles", roles);
        model.put("token", token);
        return ResponseEntity.ok(model);
    }
}

