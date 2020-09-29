package com.drbsimon.apigateway.wrapper;

import com.drbsimon.apigateway.model.entity.Visitor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class VisitorListWrapper {
    List<Visitor> visitors;
}
