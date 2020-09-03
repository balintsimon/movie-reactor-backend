package com.drbsimon.apigateway.model;

import com.drbsimon.apigateway.entity.Visitor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class VisitorListWrapper {
    List<Visitor> visitors;
}
