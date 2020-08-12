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
                // TODO: delete permit all upon endpoints revision
                .antMatchers("/*").permitAll()
                // TODO: revise endpoints
//                .antMatchers("/auth/*").permitAll()
//                .antMatchers(HttpMethod.GET, "/watchlist").hasRole("USER")
//                .antMatchers(HttpMethod.POST, "/watchlist/save/*").hasRole("USER")
//                .antMatchers(HttpMethod.DELETE, "/watchlist/delete/*").hasRole("USER")
//                .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
//                .antMatchers(HttpMethod.GET, "/me").hasRole("ADMIN")
//                .antMatchers(HttpMethod.GET, "/schedule").permitAll()
//                .antMatchers(HttpMethod.GET, "/scheduled-movies").permitAll()
//                .antMatchers(HttpMethod.GET, "/show/*").permitAll()
//                .antMatchers(HttpMethod.PUT, "/show/*").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST,"/reservation/seats").hasRole("USER")
//                .antMatchers(HttpMethod.GET,"/reservation/seats").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE,"/reservation/delete").hasRole("ADMIN")
                .anyRequest().denyAll() // anything else is denied
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
