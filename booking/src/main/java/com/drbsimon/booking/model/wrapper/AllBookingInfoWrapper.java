package com.drbsimon.booking.model.wrapper;

import com.drbsimon.booking.model.dto.AllBookingInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllBookingInfoWrapper {
    List<AllBookingInfoDTO> bookings;
}
