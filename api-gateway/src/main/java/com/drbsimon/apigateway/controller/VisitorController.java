package com.drbsimon.apigateway.controller;

import com.drbsimon.apigateway.model.entity.Visitor;
import com.drbsimon.apigateway.model.dto.VisitorsWrapperDTO;
import com.drbsimon.apigateway.service.VisitorService;
import com.drbsimon.apigateway.service.dao.VisitorDao;
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
    private final VisitorDao visitorDao;
    private final VisitorService visitorManager;

    // TODO: remap on frontend from "/users" to "/user"
    @GetMapping("/user")
    public VisitorsWrapperDTO getAllUsers() {
        return visitorDao.getAllVisitors();
    }

    @GetMapping("/user/{id}")
    public Visitor getUserById(@PathVariable("id") Long id) {
        return visitorDao.getVisitorBy(id);
    }

    @GetMapping("/me")
    public String getCurrentUserName(){
        return visitorManager.getLoggedInVisitorNameAndRole();
    }

}

