package com.drbsimon.booking.service;


import com.drbsimon.booking.model.Seat;
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
        Seat seat = restTemplate.getForObject(baseUrl + "/seat/" + seatId, Seat.class);
        return seat != null;
    }
}
