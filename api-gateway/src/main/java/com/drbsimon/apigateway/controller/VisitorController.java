package com.drbsimon.apigateway.controller;

import com.drbsimon.apigateway.entity.Visitor;
import com.drbsimon.apigateway.model.VisitorListWrapper;
import com.drbsimon.apigateway.repository.VisitorManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${main.route}")
@RequiredArgsConstructor
@RestController()
@Slf4j
public class VisitorController {
    private final VisitorManager visitorManager;

    // TODO: remap on frontend from "/users" to "/user"
    @GetMapping("/user")
    public VisitorListWrapper getAllUsers() {
        return visitorManager.getAllVisitors();
    }

    @GetMapping("/user/{id}")
    public Visitor getUserById(@PathVariable("id") Long id) {
        return visitorManager.getVisitorById(id);
    }

    // Method inherited from old design
    // TODO: check if method is needed at all => JWT has all the details on front-end!
    @GetMapping("/me")
    public String getCurrentUserName(){
        return visitorManager.getCurrentUserNameWithRoles();
    }

}

