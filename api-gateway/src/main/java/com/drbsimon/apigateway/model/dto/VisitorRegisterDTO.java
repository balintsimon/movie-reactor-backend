package com.drbsimon.apigateway.model.dto;

import com.drbsimon.apigateway.model.entity.Visitor;
import com.drbsimon.apigateway.model.Role;
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
public class VisitorRegisterDTO {
    private final VisitorRepository visitorRepository;
    private final JwtTokenServices jwtTokenServices;
    private final DataValidatorService dataValidator;
    private final PasswordEncoder passwordEncoder;
    private final VisitorLoginDTO visitorLoginDTO;

    public ResponseEntity registerUser(UserCredentialsDTO userCredentialsDTO) {

        ResponseEntity failedRegistrationMessage = checkRegistrationForError(userCredentialsDTO);
        if (failedRegistrationMessage != null) return failedRegistrationMessage;

        Visitor newVisitor = Visitor.builder()
                .username(userCredentialsDTO.getUsername())
                .password(passwordEncoder.encode(userCredentialsDTO.getPassword()))
                .firstname(userCredentialsDTO.getFirstname())
                .lastname(userCredentialsDTO.getLastname())
                .email(userCredentialsDTO.getEmail())
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();
        visitorRepository.save(newVisitor);
        return successfulRegistrationResponse(newVisitor.getUsername(), newVisitor.getRoles());
    }

    private ResponseEntity successfulRegistrationResponse(String username, List<Role> roles) {
        Map<String, Object> responseMessage = new HashMap<>();

        String token = jwtTokenServices.createToken(username, Collections.singletonList(roles.stream().toString()));
        responseMessage.put("correct", true);
        responseMessage.put("username", username);
        responseMessage.put("roles", roles);
        responseMessage.put("token", token);
        return ResponseEntity.ok(responseMessage);
    }

    // TODO: check if validation could generate error messages instead of registration service.
    // TODO: check if possible to modify to collect all possible errors in one message.
    public ResponseEntity checkRegistrationForError(UserCredentialsDTO userCredentialsDTO) {
        String username = userCredentialsDTO.getUsername();
        String password = userCredentialsDTO.getPassword();
        String firstname = userCredentialsDTO.getFirstname();
        String lastname = userCredentialsDTO.getLastname();
        String email = userCredentialsDTO.getEmail();
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

        return null;
    }

    private ResponseEntity failedRegisterMessage(String message) {
        Map<String, Object> responseMessage = new HashMap<>();
        responseMessage.put("correct", false);
        responseMessage.put("msg", message);
        return ResponseEntity.ok(responseMessage);
    }
}
