package com.drbsimon.apigateway.service.dao;

import com.drbsimon.apigateway.model.Role;
import com.drbsimon.apigateway.model.dto.UserCredentialsDTO;
import com.drbsimon.apigateway.model.dto.VisitorsWrapperDTO;
import com.drbsimon.apigateway.model.entity.Visitor;

import java.util.List;

public interface VisitorServiceDao {
    VisitorsWrapperDTO getAllVisitors();
    Visitor getVisitorBy(Long visitorId);
    Visitor getVisitorBy(String name);
    String getCurrentUserNameWithRoles();
    void save(Visitor visitor);
    void save(UserCredentialsDTO userCredentials, List<Role> roles);
}
