package com.drbsimon.apigateway.service;

import com.drbsimon.apigateway.model.Role;
import com.drbsimon.apigateway.model.dto.UserCredentialsDTO;
import com.drbsimon.apigateway.model.entity.Visitor;
import com.drbsimon.apigateway.model.dto.VisitorsWrapperDTO;
import com.drbsimon.apigateway.repository.VisitorRepository;
import com.drbsimon.apigateway.security.CustomUserDetailsService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class VisitorServiceDaoDB implements VisitorServiceDao{
    private final VisitorRepository visitorRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public VisitorsWrapperDTO getAllVisitors() {
        VisitorsWrapperDTO visitorsWrapperDTO = new VisitorsWrapperDTO();
        List<Visitor> visitors = visitorRepository.findAll();
        visitorsWrapperDTO.setVisitors(visitors);
        return visitorsWrapperDTO;
    }

    public Visitor getVisitorById(Long visitorId) {
        return visitorRepository.getById(visitorId);
    }

    public String getCurrentUserNameWithRoles(){
        String username = customUserDetailsService.findLoggedInUsername();
        UserDetails visitor = customUserDetailsService.loadUserByUsername(username);
        return username + "\n" + visitor.getAuthorities();
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
