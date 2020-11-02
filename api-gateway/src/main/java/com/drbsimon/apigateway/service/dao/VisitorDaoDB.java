package com.drbsimon.apigateway.service.dao;

import com.drbsimon.apigateway.model.Role;
import com.drbsimon.apigateway.model.dto.UserCredentialsDTO;
import com.drbsimon.apigateway.model.Visitor;
import com.drbsimon.apigateway.model.dto.VisitorsWrapperDTO;
import com.drbsimon.apigateway.repository.VisitorRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Data
@RequiredArgsConstructor
public class VisitorDaoDB implements VisitorDao {
    private final VisitorRepository visitorRepository;
    private final PasswordEncoder passwordEncoder;

    public VisitorsWrapperDTO getAllVisitors() {
        VisitorsWrapperDTO visitorsWrapperDTO = new VisitorsWrapperDTO();
        List<Visitor> visitors = visitorRepository.findAll();
        visitorsWrapperDTO.setVisitors(visitors);
        return visitorsWrapperDTO;
    }

    @Override
    public Optional<Visitor> findVisitorBy(String name) {
        return visitorRepository.findByUsername(name);
    }

    public Visitor getVisitorBy(Long visitorId) {
        return visitorRepository.getById(visitorId);
    }

    public Visitor getVisitorBy(String name) {
        return visitorRepository.getByUsername(name);
    }



    public void save(Visitor visitor) {
        visitorRepository.save(visitor);
    }

    public void save(UserCredentialsDTO userCredentials, List<Role> roles) {
        Visitor newVisitor = Visitor.builder()
                .username(userCredentials.getUsername())
                .password(passwordEncoder.encode(userCredentials.getPassword()))
                .firstname(userCredentials.getFirstname())
                .lastname(userCredentials.getLastname())
                .email(userCredentials.getEmail())
                .roles(roles)
                .gender(userCredentials.getGender())
                .build();
        visitorRepository.save(newVisitor);
    }

//    public Visitor getLoggedInVisitorFromToken() {
//        Visitor visitor = customUserDetailsService.getVisitorFromToken();
//        System.out.println("Visitor is "+ visitor);
//        return visitor;
//    }
}
