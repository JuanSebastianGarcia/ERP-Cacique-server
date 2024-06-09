package com.caciquesport.inventario.inventario.config;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/*
 * esta clase configura los cors de la aplicacion
 */
@Configuration
public class CorsConfig{

    @Bean
    CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration corseConfig = new CorsConfiguration();

        agregarReglas(corseConfig);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("**/", corseConfig);

        return source;
    }


    /*
     * agrega las reglas requeridas a los cors
     */
    private void agregarReglas(CorsConfiguration corseConfig) {

        corseConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corseConfig.setAllowedHeaders(Arrays.asList("GET","POST","PUT","DELETE"));
        corseConfig.setAllowedHeaders(Arrays.asList("*"));
    }




}
