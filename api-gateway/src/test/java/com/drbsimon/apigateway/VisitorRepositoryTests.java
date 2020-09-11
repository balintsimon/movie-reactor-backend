package com.drbsimon.apigateway;

import com.drbsimon.apigateway.entity.Visitor;
import com.drbsimon.apigateway.model.Role;
import com.drbsimon.apigateway.repository.VisitorRepository;
import com.drbsimon.apigateway.security.CustomUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

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
    public void testSaveOneVisitorSimple() {
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

    @Test
    public void testSaveSeveralVisitorSimple() {
        Visitor newUser1 = Visitor.builder()
                .username("ASD")
                .email("asd@asd.hu")
                .firstname("ASD")
                .lastname("DSA")
                .password("AS")
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();

        Visitor newUser2 = Visitor.builder()
                .username("ASD2")
                .email("asd1@asd.hu")
                .firstname("ASD")
                .lastname("DSA")
                .password("AS")
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();

        List<Visitor> newVisitors = Arrays.asList(newUser1, newUser2);
        repository.saveAll(newVisitors);
        List<Visitor> extendedVisitors = repository.findAll();

        assertThat(extendedVisitors).hasSize(visitors.size() + newVisitors.size());
    }

    @Test
    void testSaveWithNonuniqueUsername() {
        Visitor newUser1 = Visitor.builder()
                .username("ASD")
                .email("asd@asd.hu")
                .firstname("ASD")
                .lastname("DSA")
                .password("AS")
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();

        Visitor newUser2 = Visitor.builder()
                .username("ASD")
                .email("asd1@asd.hu")
                .firstname("ASD")
                .lastname("DSA")
                .password("AS")
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();

        repository.saveAndFlush(newUser1);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            repository.saveAndFlush(newUser2);
        });
    }

    @Test
    void testSaveWithNonuniqueEmail() {
        Visitor newUser1 = Visitor.builder()
                .username("ASD")
                .email("asd@asd.hu")
                .firstname("ASD")
                .lastname("DSA")
                .password("AS")
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();

        Visitor newUser2 = Visitor.builder()
                .username("ASD2")
                .email("asd@asd.hu")
                .firstname("ASD")
                .lastname("DSA")
                .password("AS")
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();

        repository.saveAndFlush(newUser1);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            repository.saveAndFlush(newUser2);
        });
    }

    @Test
    public void testFindUserByName() {
        String username = "ASD";

        Visitor newUser = Visitor.builder()
                .username(username)
                .email("asd@asd.hu")
                .firstname("ASD")
                .lastname("DSA")
                .password("AS")
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();

        repository.saveAndFlush(newUser);
        Optional<Visitor> foundVisitor = repository.findByUsername(username);

        assertThat(foundVisitor).isEqualTo(Optional.of(newUser));
    }

    @Test
    public void testCantFindUserByName() {
        Optional<Visitor> foundVisitor = repository.findByUsername("nobody");

        assertThat(foundVisitor).isEmpty();
    }

    @Test
    public void testGetByUserByName() {
        String username = "ASD";

        Visitor newUser = Visitor.builder()
                .username(username)
                .email("asd@asd.hu")
                .firstname("ASD")
                .lastname("DSA")
                .password("AS")
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();

        repository.saveAndFlush(newUser);
        Visitor foundVisitor = repository.getByUsername(username);

        assertThat(foundVisitor).isEqualTo(newUser);
    }

    @Test
    public void testGetByNonexistendUsername() {
        Visitor foundVisitor = repository.getByUsername("nobody");

        assertThat(foundVisitor).isEqualTo(null);
    }

    @Test
    public void testFindUserById() {
        List<Visitor> allVisitors = repository.findAll();
        Visitor expectedVisitor = allVisitors.get(0);
        long userId = expectedVisitor.getId();

        Optional<Visitor> foundVisitor = repository.findById(userId);

        assertThat(foundVisitor).isEqualTo(Optional.of(expectedVisitor));
    }

    @Test
    public void testFindUserByNonexistentId() {
        List<Visitor> allVisitors = repository.findAll();
        Visitor expectedVisitor = allVisitors.get(allVisitors.size() - 1);
        long lastUserId = expectedVisitor.getId();

        Optional<Visitor> foundVisitor = repository.findById(lastUserId + 1);

        assertThat(foundVisitor).isEmpty();
    }
}
