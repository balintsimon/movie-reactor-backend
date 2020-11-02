package com.drbsimon.moviecatalog.model.wrapper;

import com.drbsimon.moviecatalog.model.Show;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class ShowListWrapper {
    List<Show> shows;
}
