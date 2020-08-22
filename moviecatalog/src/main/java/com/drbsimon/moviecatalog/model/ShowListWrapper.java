package com.drbsimon.moviecatalog.model;

import com.drbsimon.moviecatalog.entity.Show;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class ShowListWrapper {
    List<Show> shows;
}
