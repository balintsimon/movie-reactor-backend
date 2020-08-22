package com.drbsimon.booking.service;


import com.drbsimon.booking.model.Show;
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
public class MovieCatalogServiceCaller {
    private final RestTemplate restTemplate;

    @Value("${moviecatalog.url}")
    private String baseUrl;

    public boolean isShowValid(Long showId) {
        Show show = restTemplate.getForObject(baseUrl + "/show/" + showId, Show.class);
        return show != null;
    }
}
