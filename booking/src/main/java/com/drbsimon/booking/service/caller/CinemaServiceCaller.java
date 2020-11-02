package com.drbsimon.booking.service.caller;

import com.drbsimon.booking.model.dto.RoomDTO;
import com.drbsimon.booking.model.dto.SeatDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

@CrossOrigin
@Service
@Slf4j
@RequiredArgsConstructor
public class CinemaServiceCaller {
    private final RestTemplate restTemplate;

    @Value("${cinema.url}")
    private String baseUrl;

    public boolean isSeatValid(Long seatId) {
        SeatDTO seat = restTemplate.getForObject(baseUrl + "/seat/" + seatId, SeatDTO.class);
        return seat != null;
    }

    public SeatDTO getSeatById(Long seatId) {
        return restTemplate.getForObject(baseUrl + "/seat/" +  seatId, SeatDTO.class);
    }

    public RoomDTO getRoomById(Long showId) {
        return restTemplate.getForObject(baseUrl + "/room/" +  showId, RoomDTO.class);
    }
}
