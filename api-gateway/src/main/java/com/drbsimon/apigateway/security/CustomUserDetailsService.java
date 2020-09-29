package com.drbsimon.apigateway.security;

import com.drbsimon.apigateway.model.entity.Visitor;
import com.drbsimon.apigateway.repository.VisitorRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private VisitorRepository visitors;

    public CustomUserDetailsService(VisitorRepository visitors) {
        this.visitors = visitors;
    }

    /**
     * Loads the user from the DB and converts it to Spring Security's internal User object.
     * Spring will call this code to retrieve a user upon login from the DB.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Visitor visitor = visitors.findByUsername(username)
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

    public Long findVisitorIdByUsername(String userName) {
        Visitor visitor = visitors.getByUsername(userName);
        if (visitor == null) return null;
        return visitor.getId();
    }

    public Visitor getVisitorFromToken() {
        String userName = findLoggedInUsername();
        return visitors.getByUsername(userName);
    }
}
