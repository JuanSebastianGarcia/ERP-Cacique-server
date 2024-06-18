package com.caciquesport.inventario.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain; 


@Configuration
@EnableWebSecurity
public class SecurityConfig{

    /*
     * Deshabilita la generacion de un token para el ingreso a la aplicacion
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf
                .disable())
                
            .authorizeHttpRequests(customRequest -> customRequest
                .requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                .requestMatchers(HttpMethod.PUT,"/api/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/**").permitAll()
                .requestMatchers(HttpMethod.DELETE,"/api/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS,"/api/**").permitAll())


            .sessionManagement(sess -> sess
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        


         return http.build();
    }



}


