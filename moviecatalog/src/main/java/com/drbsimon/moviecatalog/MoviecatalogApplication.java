package com.drbsimon.moviecatalog;

import com.drbsimon.moviecatalog.entity.Show;
import com.drbsimon.moviecatalog.model.Movie;
import com.drbsimon.moviecatalog.repository.ShowRepository;
import com.drbsimon.moviecatalog.service.MovieServiceCaller;
import com.drbsimon.moviecatalog.service.RoomServiceCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
@RequiredArgsConstructor
public class MoviecatalogApplication {
    private final ShowRepository showRepository;
    private final RoomServiceCaller roomServiceCaller;
    private final MovieServiceCaller movieServiceCaller;

    public static void main(String[] args) {
        SpringApplication.run(MoviecatalogApplication.class, args);
    }

    // Enable Swagger 2 documentation on all endpoints
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {
            LocalDate fromDate = LocalDate.now();
            for (int i = 0; i < 7; i++) {
                LocalTime startingTime = LocalTime.of(12, 0);
                for (Movie movie : movieServiceCaller.getAllShows()) {
                    Show currentShow = Show.builder()
                            .movieId(movie.getId())
                            .startingDate(fromDate)
                            .startingTime(startingTime)
                            .roomId(roomServiceCaller.getAllRooms().get(0).getId())
                            .build();
                    showRepository.save(currentShow);
                    startingTime = startingTime.plusHours(2);
                }
                fromDate = fromDate.plusDays(1);
            }
        };
    }
}
