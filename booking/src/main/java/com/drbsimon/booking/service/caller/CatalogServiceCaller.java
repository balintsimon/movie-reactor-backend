package com.drbsimon.booking.service.caller;


import com.drbsimon.booking.service.model.Show;
import com.drbsimon.booking.model.ShowListWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin
@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogServiceCaller {
    private final RestTemplate restTemplate;

    @Value("${moviecatalog.url}")
    private String baseUrl;

    public boolean isShowValid(Long showId) {
        Show show = restTemplate.getForObject(baseUrl + "/show/" + showId, Show.class);
        return show != null;
    }

    public List<Show> getAllShows() {
        ShowListWrapper shows = restTemplate.getForObject(baseUrl + "/show", ShowListWrapper.class);
        return shows.getShows();
    }

    public Show getShowById(Long showId) {
        return restTemplate.getForObject(baseUrl + "/show/" + showId, Show.class);
    }
}
