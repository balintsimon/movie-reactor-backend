package com.drbsimon.apigateway.repository;

import com.drbsimon.apigateway.model.entity.Visitor;
import com.drbsimon.apigateway.wrapper.VisitorListWrapper;
import com.drbsimon.apigateway.security.CustomUserDetailsService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class VisitorManager {
    private final VisitorRepository visitorRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public VisitorListWrapper getAllVisitors() {
        VisitorListWrapper visitorListWrapper = new VisitorListWrapper();
        List<Visitor> visitors = visitorRepository.findAll();
        visitorListWrapper.setVisitors(visitors);
        return visitorListWrapper;
    }

    public Visitor getVisitorById(Long visitorId) {
        return visitorRepository.getById(visitorId);
    }

    public String getCurrentUserNameWithRoles(){
        String username = customUserDetailsService.findLoggedInUsername();
        UserDetails visitor = customUserDetailsService.loadUserByUsername(username);
        return username + "\n" + visitor.getAuthorities();
    }

//    public Visitor getLoggedInVisitorFromToken() {
//        Visitor visitor = customUserDetailsService.getVisitorFromToken();
//        System.out.println("Visitor is "+ visitor);
//        return visitor;
//    }
}
