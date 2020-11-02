package com.drbsimon.apigateway.controller;

import com.drbsimon.apigateway.model.Gender;
import com.drbsimon.apigateway.model.dto.UserCredentialsDTO;
import com.drbsimon.apigateway.security.service.AuthService;
import com.drbsimon.apigateway.repository.VisitorRepository;
import com.drbsimon.apigateway.security.service.ParseVisitorSecurityService;
import com.drbsimon.apigateway.security.service.JwtTokenServices;
import com.drbsimon.apigateway.service.dao.VisitorDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@WebMvcTest(controllers = AuthService.class)
@ActiveProfiles("test")
class AuthControllerLoginTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    // Dependencies necessary to start tests
    @MockBean
    private ZuulProperties zuulProperties;

    @MockBean
    private VisitorRepository repository;

    @MockBean
    private VisitorDao visitorDao;

    @MockBean
    private ParseVisitorSecurityService parseVisitorSecurityService;

    @MockBean
    private JwtTokenServices jwtTokenServices;

    @MockBean
    private HttpServletResponse httpResponse;

    // Further mocking
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private UserCredentialsDTO loggedInUser;
    private String userName = "Test";
    private String password = "test";

    @Test
    public void testContextLoaded() {
        assertThat(authService).isNotNull();
    }

    @BeforeEach
    public void init() {
        loggedInUser = new UserCredentialsDTO().builder()
                .username(userName)
                .password(password)
                .gender(Gender.GENERAL)
                .firstname("Firstname")
                .lastname("Lastname")
                .email("test@test.com")
                .build();

    }

    // TODO: fix test to use or mock HttpResponse
//    @Test
//    public void testValidLoginCall() {
//        GrantedAuthority grantedAuthority = mock(GrantedAuthority.class);
//        List<Role> roles = new ArrayList<>();
//        roles.add(Role.ROLE_USER);
//        given(grantedAuthority.getAuthority()).willReturn(String.valueOf(roles));
//        given(authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(userName, password)))
//                .willReturn((Authentication) loggedInUser);
//
//        Authentication authentication = mock(Authentication.class);
//        authentication.setAuthenticated(true);
//        given(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password))).willReturn(authentication);
//
//        ResponseEntity actual = authService.loginUser(loggedInUser, httpResponse);
//        ResponseEntity expected = authService.validLoginResponse(authentication, httpResponse);
//        assertThat(actual).isEqualTo(expected);
//    }

    @Test
    public void testInvalidLoginCall() {
        AuthenticationException error = mock(AuthenticationException.class);
        given(authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userName, password)))
                .willThrow(error);

        assertThat(authService.loginUser(loggedInUser, httpResponse)).isEqualTo(authService.invalidLoginMessage());
    }
}