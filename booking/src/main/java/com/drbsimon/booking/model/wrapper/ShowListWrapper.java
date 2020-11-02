package com.drbsimon.booking.model.wrapper;

import com.drbsimon.booking.service.model.Show;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class ShowListWrapper {
    List<Show> shows;
}
