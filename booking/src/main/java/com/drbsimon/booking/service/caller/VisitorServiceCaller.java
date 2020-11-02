package com.drbsimon.booking.service.caller;

import com.drbsimon.booking.service.model.VisitorDTO;
import com.drbsimon.booking.model.wrapper.VisitorListWrapper;
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
public class VisitorServiceCaller {

    private final RestTemplate restTemplate;

    @Value("${visitor.url}")
    private String baseUrl;

    public List<VisitorDTO> getAllVisitors() {
        VisitorListWrapper visitors = restTemplate.getForObject(baseUrl + "/user", VisitorListWrapper.class);
        return visitors.getVisitors();
    }

    public VisitorDTO getVisitorById(Long visitorId) {
        return restTemplate.getForObject(baseUrl + "/user/" + visitorId, VisitorDTO.class);
    }
}
