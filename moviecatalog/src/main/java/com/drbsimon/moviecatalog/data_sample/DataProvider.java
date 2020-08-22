package com.drbsimon.moviecatalog.data_sample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

//@Component
@Service
public class DataProvider implements CommandLineRunner {

    private final ShowCreator showCreator;

    public DataProvider(ShowCreator showCreator) {
        this.showCreator = showCreator;
    }

    @Override
    public void run(String... args) {
//        showCreator.createWeeklyScheduleData(LocalDate.of(2020, 7, 30));
        LocalDate now = LocalDate.now();
        System.out.println("Local Date now is: " + now);
        showCreator.createWeeklyScheduleData(now);
    }
}
