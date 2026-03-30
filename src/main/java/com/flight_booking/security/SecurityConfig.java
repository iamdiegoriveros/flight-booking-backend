package com.flight_booking.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/flight/**").hasRole("ADMIN")
                        .requestMatchers("/api/airline/**").hasRole("ADMIN")
                        .requestMatchers("/api/aircraft/**").hasRole("ADMIN")
                        .requestMatchers("/api/booking/**").hasRole("USER")
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public String genera(){
//        System.out.println(passwordEncoder().encode("admin123"));
//        System.out.println(passwordEncoder().encode("diego123"));
//
//        return "password generada";
//    }

}
