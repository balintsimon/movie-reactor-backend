package com.drbsimon.apigateway;

import com.drbsimon.apigateway.entity.Visitor;
import com.drbsimon.apigateway.model.Role;
import com.drbsimon.apigateway.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableZuulProxy
@EnableSwagger2
@EnableEurekaClient
@RequiredArgsConstructor
public class ApiGatewayApplication {
    private final ZuulProperties zuulProperties;
    private final VisitorRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
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

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            List<SwaggerResource> resources = new ArrayList<>();
            zuulProperties.getRoutes().values().stream()
                    .forEach(route -> resources
                            .add(createResource(route.getServiceId(), route.getServiceId(), "version1")));
            return resources;
        };
    }

    private SwaggerResource createResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation("/" + location + "/v2/api-docs");
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {
            PasswordEncoder passwordEncoder = getEncoder();
            Visitor admin = Visitor.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .email("admin@gmail.com")
                    .firstname("Klari")
                    .lastname("Tolnai")
                    .roles(Arrays.asList(Role.ROLE_ADMIN))
                    .build();
            repository.save(admin);

            Visitor user = Visitor.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user"))
                    .email("user@gmail.com")
                    .firstname("Dani")
                    .lastname("Kovats D.")
                    .roles(Collections.singletonList(Role.ROLE_USER))
                    .build();
            repository.save(user);
        };
    }
}
