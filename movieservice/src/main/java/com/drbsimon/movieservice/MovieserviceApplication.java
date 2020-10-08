package com.drbsimon.movieservice;

import com.drbsimon.movieservice.entity.Movie;
import com.drbsimon.movieservice.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
@RequiredArgsConstructor
public class MovieserviceApplication {
    private final MovieRepository movieRepository;

    public static void main(String[] args) {
        SpringApplication.run(MovieserviceApplication.class, args);
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
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {
//            List<Integer> movieIds = Arrays.asList(496243, 495764, 475557, 155, 501907);
            List<Integer> movieIds = Arrays.asList(501907, 556678, 495764, 76341, 106646);
            List<Integer> runtime = Arrays.asList(110, 125, 110, 130, 180);

            for (Integer i = 0; i < movieIds.size(); i++) {
                Movie newMovie = Movie.builder()
                        .movieDbId(movieIds.get(i))
                        .runtime(runtime.get(i))
                        .build();
                movieRepository.save(newMovie);
            }
        };
    }
}
