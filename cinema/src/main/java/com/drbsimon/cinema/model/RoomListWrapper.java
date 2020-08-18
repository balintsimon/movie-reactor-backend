package com.drbsimon.cinema.model;

import com.drbsimon.cinema.entity.Room;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class RoomListWrapper {
    List<Room> rooms;
}
