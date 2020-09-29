package com.drbsimon.apigateway.model.dto;

import com.drbsimon.apigateway.repository.VisitorRepository;
import com.drbsimon.apigateway.security.DataValidatorService;
import com.drbsimon.apigateway.security.JwtTokenServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class VisitorLoginDTO {
    private final VisitorRepository visitorRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenServices jwtTokenServices;
    private final DataValidatorService dataValidator;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity loginUser(UserCredentialsDTO userCredentialsDTO) {
        try {
            String username = userCredentialsDTO.getUsername();
            String password = userCredentialsDTO.getPassword();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            return validLoginResponse(authentication, username);
        } catch (AuthenticationException e) {
            return invalidLoginMessage();
        }
    }

    private ResponseEntity validLoginResponse(Authentication authentication, String username) {
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String token = jwtTokenServices.createToken(username, roles);

        return validLoginMessage(new ValidMessageFields(true, username, roles, token));
    }

    private ResponseEntity validLoginMessage(ValidMessageFields validMessageFields) {
        Map<String, Object> responseEntityBody = new HashMap<>();
        responseEntityBody.put("correct", validMessageFields.getIsCorrect());
        responseEntityBody.put("username", validMessageFields.getUsername());
        responseEntityBody.put("roles", validMessageFields.getRoles());
        responseEntityBody.put("token", validMessageFields.getToken());
        return ResponseEntity.ok(responseEntityBody);
    }

    private ResponseEntity invalidLoginMessage() {
        Map<String, Object> responseEntityBody = new HashMap<>();
        responseEntityBody.put("correct", false);
        responseEntityBody.put("msg", "Username or/and password is not correct!");
        return ResponseEntity.ok(responseEntityBody);
    }

    @Data
    @AllArgsConstructor
    private class ValidMessageFields {
        private Boolean isCorrect;
        private String username;
        private List<String> roles;
        private String token;
    }
}
