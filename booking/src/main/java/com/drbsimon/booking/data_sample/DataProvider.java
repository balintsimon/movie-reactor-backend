package com.drbsimon.booking.data_sample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class DataProvider implements CommandLineRunner {

    private final BookingCreator bookingCreator;

    public DataProvider(BookingCreator bookingCreator) {
        this.bookingCreator = bookingCreator;
    }

    @Override
    public void run(String... args) {
        bookingCreator.createBookings();
    }
}
