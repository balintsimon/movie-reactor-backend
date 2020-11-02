package com.drbsimon.apigateway.service.dao;

import com.drbsimon.apigateway.model.Role;
import com.drbsimon.apigateway.model.dto.UserCredentialsDTO;
import com.drbsimon.apigateway.model.dto.VisitorsWrapperDTO;
import com.drbsimon.apigateway.model.Visitor;

import java.util.List;
import java.util.Optional;

public interface VisitorDao {
    VisitorsWrapperDTO getAllVisitors();
    Visitor getVisitorBy(Long visitorId);
    Visitor getVisitorBy(String name);
    Optional<Visitor> findVisitorBy(String name);
    void save(Visitor visitor);
    void save(UserCredentialsDTO userCredentials, List<Role> roles);
}
