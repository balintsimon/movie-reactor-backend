package com.drbsimon.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@CrossOrigin
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenServices jwtTokenServices;

    public SecurityConfig(JwtTokenServices jwtTokenServices) {
        this.jwtTokenServices = jwtTokenServices;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()

                .antMatchers(HttpMethod.GET, "/user", "/user/**").permitAll() // Needed for data initialization
                .antMatchers(HttpMethod.GET, "/apigateway/user", "/apigateway/user/**").permitAll() // Needed for data initialization
                // TODO: modify it to only authorized access
                /* Bug description
                 * Booking service requires intra-microservice communication. However, user token is stripped while going through gateway
                 * or was non-existent (because user wasn't logged in), therefore this endpoint is locked for the booking service.
                 * This results in the booking service not attaching information, so it does not work.
                 * */

                .antMatchers( "/watchlist", "/watchlist/**").authenticated()
//                .antMatchers( "/watchlist", "/watchlist/**").hasRole("USER") // TODO: switch to this if admin loses capability to add to watchlist
                .antMatchers(HttpMethod.GET, "/cinema/room/**").permitAll()
                .antMatchers(HttpMethod.GET, "/cinema/seat", "/cinema/seat/**").permitAll()
                .antMatchers(HttpMethod.GET, "/moviecatalog/schedule", "/moviecatalog/show", "/moviecatalog/show/**").permitAll()
                .antMatchers(HttpMethod.GET, "/booking/reservation/show/**").permitAll()


                .antMatchers(HttpMethod.GET, "/booking/reservation", "/booking/reservation/**").permitAll()
                .antMatchers(HttpMethod.POST, "/booking/reservation", "/booking/reservation/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/booking/reservation", "/booking/reservation/**").authenticated()

                .anyRequest().denyAll()
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenServices), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.setAllowCredentials(true);
        // configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-*", "Origin", "Content-type", "Accept", "Authorization"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
