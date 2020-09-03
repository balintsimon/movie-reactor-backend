package com.drbsimon.booking.service.caller;

import com.drbsimon.booking.service.model.Visitor;
import com.drbsimon.booking.model.VisitorListWrapper;
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

    public List<Visitor> getAllVisitors() {
        VisitorListWrapper visitors = restTemplate.getForObject(baseUrl + "/user", VisitorListWrapper.class);
        return visitors.getVisitors();
    }

    public Visitor getLoggedInUser() {
        return restTemplate.getForObject(baseUrl + "/loggedin", Visitor.class);
    }

    public Visitor getVisitorById(Long visitorId) {
        return restTemplate.getForObject(baseUrl + "/user/" + visitorId, Visitor.class);
    }
}
