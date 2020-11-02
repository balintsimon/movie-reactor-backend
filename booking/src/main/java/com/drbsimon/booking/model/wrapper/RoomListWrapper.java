package com.drbsimon.booking.model.wrapper;

import com.drbsimon.booking.model.dto.RoomDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class RoomListWrapper {
    List<RoomDTO> rooms;
}
