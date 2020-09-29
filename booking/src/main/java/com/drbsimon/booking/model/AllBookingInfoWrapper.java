package com.drbsimon.booking.model;

import com.drbsimon.booking.service.model.AllBookingInfo;
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
    List<AllBookingInfo> bookings;
}
