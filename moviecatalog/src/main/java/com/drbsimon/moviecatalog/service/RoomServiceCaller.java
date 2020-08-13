package com.drbsimon.moviecatalog.service;

import com.drbsimon.moviecatalog.model.Room;
import com.drbsimon.moviecatalog.repository.ShowRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class RoomServiceCaller {
    private RestTemplate restTemplate;

    @Value("${cinema.url}")
    private String cinemaBaseUrl;

    public List<Room> getAllRooms() {
        return restTemplate.getForObject(cinemaBaseUrl + "/room", List.class);
    }

}
