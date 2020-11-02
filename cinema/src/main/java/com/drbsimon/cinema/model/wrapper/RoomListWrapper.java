package com.drbsimon.cinema.model.wrapper;

import com.drbsimon.cinema.model.Room;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class RoomListWrapper {
    List<Room> rooms;
}
