package com.TaskTracker.config;

import com.TaskTracker.filter.JwtRequestFilter;
import com.TaskTracker.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter; //to filter http requests / require them to contain JWTs in the Authorization header

    @Autowired
    private MyUserDetailsService myUserDetailsService; //for fetching usernames and matching passwords

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //http filter properties
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) //to prevent CORS errors / allow the app to work in LAN
                .csrf(csrf -> csrf.disable()) //disable CSRF (not necessary since we're using JWTs and not cookies)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //no sessions, the app is stateless
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll() //allow authentication
                        .requestMatchers("/h2-console/**").permitAll() //allow H2 console access
                        .requestMatchers("/", "/index.html", "/static/**", "/favicon.ico").permitAll() //allow access to frontend (homepage and rest of the app) and the favicon
                        .anyRequest().authenticated()) //secure all other endpoints
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); //add the JWT filter

        return http.build();
    }

    //this is used for user authentication, specifically password hashing and matching
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    //password hasher
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true); //allow credentials (specifically, JWTs in the Authorization header)
        configuration.addAllowedOriginPattern("*"); //allow access from everywhere on the LAN
        configuration.addAllowedHeader("*"); //allow all headers
        configuration.addAllowedMethod("*"); //allow all HTTP methods

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); //apply configuration to all endpoints
        return source;
    }

}
