package com.drbsimon.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Service
@Slf4j
@RequiredArgsConstructor
public class VisitorCaller {

    @Value("${visitor.url}")
    private String baseUrl;
    // apigateway
}
