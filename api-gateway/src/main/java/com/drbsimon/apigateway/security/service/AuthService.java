package com.drbsimon.apigateway.security.service;

import com.drbsimon.apigateway.model.Gender;
import com.drbsimon.apigateway.model.dto.UserCredentialsDTO;
import com.drbsimon.apigateway.model.entity.Visitor;
import com.drbsimon.apigateway.repository.VisitorRepository;
import com.drbsimon.apigateway.utils.PatternUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {
    private final VisitorRepository visitorRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenServices jwtTokenServices;
    private final PatternUtil dataValidator;
    private final PasswordEncoder passwordEncoder;

    public static final String TOKEN_COOKIE_NAME = "JWT";

    public ResponseEntity loginUser(UserCredentialsDTO userCredentials, HttpServletResponse response) {
        try {
            String username = userCredentials.getUsername();
            String password = userCredentials.getPassword();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            return validLoginResponse(authentication, response);
        } catch (AuthenticationException e) {
            return invalidLoginMessage();
        }
    }

    public ResponseEntity validLoginResponse(Authentication authentication, HttpServletResponse response) {
        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String token = jwtTokenServices.createToken(username, roles);

        Visitor visitor = visitorRepository.getByUsername(username);
        Gender gender = visitor.getGender();
        ResponseCookie tokenCookie = createTokenCookie(token, Duration.ofSeconds(-1L));
        response.addHeader("Set-Cookie", tokenCookie.toString());

        return validLoginMessage(new ValidMessageFields(true, username, roles, gender));
    }

    private ResponseEntity validLoginMessage(ValidMessageFields validMessageFields) {
        Map<String, Object> responseEntityBody = new HashMap<>();
        responseEntityBody.put("correct", validMessageFields.getIsCorrect());
        responseEntityBody.put("username", validMessageFields.getUsername());
        responseEntityBody.put("roles", validMessageFields.getRoles());
        responseEntityBody.put("gender", validMessageFields.getGender());
        return ResponseEntity.ok(responseEntityBody);
    }

    public ResponseEntity invalidLoginMessage() {
        Map<String, Object> responseEntityBody = new HashMap<>();
        responseEntityBody.put("correct", false);
        responseEntityBody.put("msg", "Username or/and password is not correct!");
        return ResponseEntity.ok(responseEntityBody);
    }

    public ResponseCookie createTokenCookie(String jwt, Duration maxAge) {
        return ResponseCookie.from(TOKEN_COOKIE_NAME, jwt)
                .sameSite("Strict")
                .httpOnly(true)
                .path("/")
                .maxAge(maxAge)
                .build();
    }

    public void logout(HttpServletResponse response) {
        invalidateTokenCookie(response);
    }

    private void invalidateTokenCookie(HttpServletResponse response) {
        log.info("Invalidating JWT cookie...");
        ResponseCookie cookie = createTokenCookie("", Duration.ZERO);
        response.addHeader("Set-Cookie", cookie.toString());
    }

    @Data
    @AllArgsConstructor
    private class ValidMessageFields {
        private Boolean isCorrect;
        private String username;
        private List<String> roles;
        private Gender gender;
    }
}
