package com.drbsimon.booking.model;

import com.drbsimon.booking.service.model.Visitor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class VisitorListWrapper {
    List<Visitor> visitors;
}
