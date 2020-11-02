package com.drbsimon.apigateway.security.service;

import com.drbsimon.apigateway.model.Visitor;
import com.drbsimon.apigateway.service.dao.VisitorDao;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ParseVisitorSecurityService implements UserDetailsService {

    private VisitorDao visitorDao;

    public ParseVisitorSecurityService(VisitorDao visitorDao) {
        this.visitorDao = visitorDao;
    }

    /**
     * Loads the user from the DB and converts it to Spring Security's internal User object.
     * Spring will call this code to retrieve a user upon login from the DB.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Visitor visitor = visitorDao.findVisitorBy(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
        return new User(visitor.getUsername(), visitor.getPassword(),
                visitor.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList()));
    }

    public String findLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    public Visitor parseVisitorFromToken() {
        String userName = findLoggedInUsername();
        return visitorDao.getVisitorBy(userName);
    }
}