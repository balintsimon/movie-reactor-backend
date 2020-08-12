package com.codecool.moviereactorapplication.controller;

import com.codecool.moviereactorapplication.entity.Visitor;
import com.codecool.moviereactorapplication.repository.VisitorRepository;
import com.codecool.moviereactorapplication.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController()
public class VisitorController {

    private final CustomUserDetailsService customUserDetailsService;

    private final VisitorRepository allVisitors;

    @GetMapping("/users")
    public List<Visitor> getAllUsers() {
        return allVisitors.findAll();
    }

    @GetMapping("/me")
    public String currentUser(){
        String username = customUserDetailsService.findLoggedInUsername();
        UserDetails visitor = customUserDetailsService.loadUserByUsername(username);
        return username + "\n" + visitor.getAuthorities();
    }

}

