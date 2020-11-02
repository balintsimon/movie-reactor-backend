package com.drbsimon.apigateway.service;

import com.drbsimon.apigateway.model.Role;
import com.drbsimon.apigateway.model.dto.UserCredentialsDTO;
import com.drbsimon.apigateway.model.dto.VisitorsWrapperDTO;
import com.drbsimon.apigateway.model.entity.Visitor;

import java.util.List;

public interface VisitorServiceDao {
    VisitorsWrapperDTO getAllVisitors();
    Visitor getVisitorById(Long visitorId);
    String getCurrentUserNameWithRoles();
    void save(Visitor visitor);
    void save(UserCredentialsDTO userCredentials, List<Role> roles);
}
