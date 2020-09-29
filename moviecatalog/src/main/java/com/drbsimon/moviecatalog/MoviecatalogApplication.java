package com.drbsimon.moviecatalog;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
public class MoviecatalogApplication {

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


    // TODO: create shows should run after services are running
    // NB: this code runs into NPE because restTemplate() is needed before it is initialized in main
//    @Bean
//    @Profile("production")
//    public CommandLineRunner init() {
//        return args -> {
//            movieServiceCaller.test();
//            LocalDate fromDate = LocalDate.now();
//            for (int i = 0; i < 7; i++) {
//                LocalTime startingTime = LocalTime.of(12, 0);
//                List<Movie> movies = movieServiceCaller.getAllShows();
//                if (movies != null) {
//                    for (Movie movie : movies) {
//                        Show currentShow = Show.builder()
//                                .movieId(movie.getId())
//                                .startingDate(fromDate)
//                                .startingTime(startingTime)
//                                .roomId(roomServiceCaller.getAllRooms().get(0).getId())
//                                .build();
//                        showRepository.save(currentShow);
//                        startingTime = startingTime.plusHours(2);
//                    }
//                }
//                fromDate = fromDate.plusDays(1);
//            }
//        };
//    }
}
