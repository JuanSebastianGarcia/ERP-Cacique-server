package com.caciquesport.inventario.inventario.config;

import java.io.IOException;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Duration;


import org.springframework.stereotype.Component;


@WebFilter("/*")
@Component
public class RateLimitingFilter implements Filter{


    /*
     * clase que maneja la cantidad de solicitudes
     */
    private final Bucket bucket;
    

    
    /*
     * Establecer las especificaciones con las que se necesita que el filtro opere
     */
    public RateLimitingFilter() {
        Bandwidth limit = Bandwidth.classic(30, Refill.greedy(1, Duration.ofSeconds(3)));
        this.bucket = Bucket.builder().addLimit(limit).build();
    }




    /*
     * Se encarga de filtrar la cantidad de solicitudes que se hacen en un lapso de tiempo
     * si se hacen mas solicitudes de lo permitido por minuto el sistema evitara las futuras solicitudes
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
            
        //LOGGER.info("Bucket state: " + bucket.getAvailableTokens());

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            response.getWriter().write("demaciadas solicitudes");
        }

    }


}
