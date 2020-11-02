package com.drbsimon.apigateway.model.dto;

import com.drbsimon.apigateway.model.Visitor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class VisitorsWrapperDTO {
    List<Visitor> visitors;
}
