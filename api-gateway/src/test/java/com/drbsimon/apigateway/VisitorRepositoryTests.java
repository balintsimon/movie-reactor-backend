package com.drbsimon.apigateway;

import com.drbsimon.apigateway.controller.filter.PreFilter;
import com.drbsimon.apigateway.entity.Visitor;
import com.drbsimon.apigateway.model.Role;
import com.drbsimon.apigateway.repository.VisitorManager;
import com.drbsimon.apigateway.repository.VisitorRepository;
import com.drbsimon.apigateway.security.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class VisitorRepositoryTests {

    @Autowired
    private VisitorRepository repository;

    @MockBean
    private ZuulProperties zuulProperties;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    List<Visitor> visitors;

    @Test
    public void testRepositoryLoads() throws Exception {
        assertThat(repository).isNotNull();
    }

    @BeforeEach
    void setUp() {
        visitors = new ArrayList<>();

        Visitor admin = Visitor.builder()
                .username("admin")
                .password("admin")
                .email("admin@gmail.com")
                .firstname("Klari")
                .lastname("Tolnai")
                .roles(Arrays.asList(Role.ROLE_ADMIN))
                .build();
        repository.save(admin);

        Visitor user = Visitor.builder()
                .username("user")
                .password("user")
                .email("user@gmail.com")
                .firstname("Dani")
                .lastname("Kovats D.")
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();
        repository.save(user);

        visitors.addAll(Arrays.asList(admin, user));
    }

    @Test
    public void saveOneVisitorSimple() {
        Visitor newUser = Visitor.builder()
                .username("ASD")
                .email("asd@asd.hu")
                .firstname("ASD")
                .lastname("DSA")
                .password("AS")
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();

        repository.save(newUser);

        List<Visitor> extendedVisitors = repository.findAll();
        assertThat(extendedVisitors).hasSize(visitors.size() + 1);
    }

}
