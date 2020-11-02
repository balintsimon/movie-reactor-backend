package com.drbsimon.apigateway.controller;

import com.drbsimon.apigateway.model.entity.Visitor;
import com.drbsimon.apigateway.model.dto.VisitorsWrapperDTO;
import com.drbsimon.apigateway.service.dao.VisitorServiceDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "${main.route}")
@RequiredArgsConstructor
@RestController()
@Slf4j
public class VisitorController {
    private final VisitorServiceDao visitorServiceDaoDB;

    // TODO: remap on frontend from "/users" to "/user"
    @GetMapping("/user")
    public VisitorsWrapperDTO getAllUsers() {
        return visitorServiceDaoDB.getAllVisitors();
    }

    @GetMapping("/user/{id}")
    public Visitor getUserById(@PathVariable("id") Long id) {
        return visitorServiceDaoDB.getVisitorBy(id);
    }

    // Method inherited from old design
    // TODO: check if method is needed at all => JWT has all the details on front-end!
    @GetMapping("/me")
    public String getCurrentUserName(){
        return visitorServiceDaoDB.getCurrentUserNameWithRoles();
    }

}

