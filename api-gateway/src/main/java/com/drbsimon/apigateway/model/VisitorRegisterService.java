package com.drbsimon.apigateway.model;

import com.drbsimon.apigateway.entity.Visitor;
import com.drbsimon.apigateway.repository.VisitorRepository;
import com.drbsimon.apigateway.security.DataValidatorService;
import com.drbsimon.apigateway.security.JwtTokenServices;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class VisitorRegisterService {
    private final VisitorRepository visitorRepository;
    private final JwtTokenServices jwtTokenServices;
    private final DataValidatorService dataValidator;
    private final PasswordEncoder passwordEncoder;
    private final VisitorLoginService visitorLoginService;

    public ResponseEntity registerUser(UserCredentials userCredentials) {
        String username = userCredentials.getUsername();
        String password = userCredentials.getPassword();
        String firstname = userCredentials.getFirstname();
        String lastname = userCredentials.getLastname();
        String email = userCredentials.getEmail();
        Gender gender = userCredentials.getGender();

        Map<Object, Object> model = new HashMap<>();

        ResponseEntity failedRegisterMessage = validateRegistryData(userCredentials);
        if (failedRegisterMessage != null) return failedRegisterMessage;

        Visitor newVisitor = Visitor.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .gender(gender)
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();
        visitorRepository.save(newVisitor);

        List<String> roles = Collections.singletonList(Role.ROLE_USER.toString());
        String token = jwtTokenServices.createToken(username, roles);
        model.put("correct", true);
        model.put("username", username);
        model.put("roles", roles);
        model.put("token", token);
        return ResponseEntity.ok(model);
    }

    private ResponseEntity validateRegistryData(UserCredentials userCredentials) {
        String username = userCredentials.getUsername();
        String password = userCredentials.getPassword();
        String firstname = userCredentials.getFirstname();
        String lastname = userCredentials.getLastname();
        String email = userCredentials.getEmail();
        Gender gender = userCredentials.getGender();
        List<String> errorList = new ArrayList<>();

        if (visitorRepository.findByUsername(username).isPresent()) {
            return failedRegisterMessage("Username already exists! Please choose a different username!");
        }

        if (!dataValidator.isValidUsername(username, errorList)) {
            String error = String.join(" , ", errorList);
            String errorMessage = "Username should have " + error + "!";
            return failedRegisterMessage(errorMessage);
        }

        if (!dataValidator.isValidPassword(password, errorList)) {
            String error = String.join(" , ", errorList);
            String errorMessage = "Password should contain " + error + "!";
            return failedRegisterMessage(errorMessage);
        }

        if (!dataValidator.isValidName(firstname, errorList)) {
            String error = String.join(" , ", errorList);
            String errorMessage = "Firstname " + error + "!";
            return failedRegisterMessage(errorMessage);
        }

        if (!dataValidator.isValidName(lastname, errorList)) {
            String error = String.join(" , ", errorList);
            String errorMessage = "Lastname " + error + "!";
            return failedRegisterMessage(errorMessage);
        }

        if (!dataValidator.isValidEmail(email, errorList)) {
            return failedRegisterMessage("Not valid email!");
        }

        if (!dataValidator.isValidGender(gender, errorList)) {
            return failedRegisterMessage("Not a vaild gender!");
        }
        return null;
    }

    private ResponseEntity failedRegisterMessage(String message) {
        Map<String, Object> responseMessage = new HashMap<>();
        responseMessage.put("correct", false);
        responseMessage.put("msg", message);
        return ResponseEntity.ok(responseMessage);
    }
}
