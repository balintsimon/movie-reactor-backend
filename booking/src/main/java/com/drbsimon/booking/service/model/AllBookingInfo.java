package com.drbsimon.apigateway.model;

import com.drbsimon.apigateway.entity.Visitor;
import com.drbsimon.apigateway.service.model.Seat;
import com.drbsimon.apigateway.service.model.Show;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllBookingInfo {
    Long id;
    Visitor visitor;
    Seat seat;
    Show show;
    int movieId;
}
