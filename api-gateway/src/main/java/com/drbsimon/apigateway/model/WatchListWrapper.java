package com.drbsimon.apigateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
public class WatchListWrapper {
    List<Integer> watchlist;
}
