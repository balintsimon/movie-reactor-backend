package com.drbsimon.booking.model.wrapper;

import com.drbsimon.booking.model.dto.VisitorDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class VisitorListWrapper {
    List<VisitorDTO> visitors;
}
