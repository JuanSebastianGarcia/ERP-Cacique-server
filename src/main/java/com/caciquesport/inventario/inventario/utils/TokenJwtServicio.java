package com.caciquesport.inventario.inventario.utils;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class TokenJwtServicio extends OncePerRequestFilter{

    @Value("${jwt.secret}")
    private String clave;

    
    /*
     *crear un token para la validacion del usuario
     *@param email- correo del usuario que ingresa
     *@param claims - datos necesarios para crear el token(tipoEmpleado, id, nombre) 
     * 
     * @return - token generado con base en la informacion del empleado
     */
    public String generarToken(String email, Map<String,Object> claims){

        Instant now= Instant.now();

        String token=Jwts.builder().
            addClaims(claims).
            setSubject(email).
            setIssuedAt(Date.from(now)).
            setExpiration(Date.from(now.plus(1L, ChronoUnit.DAYS)))
            .signWith(getKey()).
            compact();

        return token;
    }




    /**
     * Devuelve la clave de firma para JWTs, decodificada desde una versión en base64.
     * 
     * @return - llave codificada
     */      
    private Key getKey(){
        return new SecretKeySpec(Base64.getDecoder().decode(clave),
                SignatureAlgorithm.HS256.getJcaName());
    }



    /*
     * extraer los claims de un toker.
     * 
     * @param token - representa el token de un empleado.
     * 
     * @return -  claims extraidos del token
     */
    public Jws<Claims> extraerClaims(String token) throws ExpiredJwtException,
    UnsupportedJwtException, MalformedJwtException, IllegalArgumentException {

        Jws<Claims> claims= Jwts.parserBuilder()
            .setSigningKey( getKey() )
            .build()
            .parseClaimsJws(token);

        return claims;
    }



 
    /*
     * Configura las cabeceras CORS en la respuesta HTTP
     * 
     * @param request - 
     */
    public void establecerPermisos(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, Authorization");
        response.addHeader("Access-Control-Allow-Credentials", "true");

    }





    /*
     * procesar las solicitudes http verificando si es un preflight y validando el token
     * 
     * @param request - La solicitud HTTP entrante que contiene información del cliente, cabeceras, y más.
     * @param response - La respuesta HTTP que se enviará al cliente, donde se pueden establecer estados, cabeceras, y cuerpo.
     * @param filterChain - Una cadena de filtros que permite pasar la solicitud y la respuesta a otros filtros o al recurso final.
     */
    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        

            establecerPermisos(response);

            if(request.getMethod().equals("OPTIONS")){
                response.setStatus(HttpServletResponse.SC_OK);
            }else{
                validarApis(request,response,filterChain);
            }

    }


    /*
     * vericar si las apis accedidas requieren control de acceso y si lo requieren, se procede a validar el token
     * 
     * @param request - La solicitud HTTP entrante que contiene información del cliente, cabeceras, y más.
     * @param response - La respuesta HTTP que se enviará al cliente, donde se pueden establecer estados, cabeceras, y cuerpo.
     * @param filterChain - Una cadena de filtros que permite pasar la solicitud y la respuesta a otros filtros o al recurso final.
     * 
     */
    private void validarApis(HttpServletRequest request, HttpServletResponse response,FilterChain filterChain) throws IOException, ServletException {
        
        String requestURI = request.getRequestURI();

        if(requestURI.startsWith("/api/manejoProducto") || requestURI.startsWith("/api/manejoEmpleado")){
            validarToken(request,response,filterChain);
        }else{
            filterChain.doFilter(request, response);
        }


    }




    
    /*
     * verificar la existencia del token
     * 
     * @param request - La solicitud HTTP entrante que contiene información del cliente, cabeceras, y más.
     * @param response - La respuesta HTTP que se enviará al cliente, donde se pueden establecer estados, cabeceras, y cuerpo.
     * @param filterChain - Una cadena de filtros que permite pasar la solicitud y la respuesta a otros filtros o al recurso final.
     */
    private void validarToken(HttpServletRequest request, HttpServletResponse response,FilterChain filterChain) throws IOException, ServletException{
       
        String token = getToken(request);

        if(token==null){
            crearRespuestaError("el token no existe", HttpServletResponse.SC_FORBIDDEN,response);
        }else{
            Jws<Claims> claims = extraerClaims(token);

            boolean acceso=validarAcceso(claims,request.getRequestURI());

            if (acceso) {
                filterChain.doFilter(request, response);
            }else{
                crearRespuestaError("no cuenta con los permisos de acceso", HttpServletResponse.SC_FORBIDDEN, response);
            }

        }

    }




    /*
     * verificar que el rol en el token si pueda acceder al recurso solicitado. 
     * el jefe puede acceder a la api de jefe y el empleado a la api de empleado
     * 
     * @param claims - datos de acceso en el token
     */
    private boolean validarAcceso(Jws<Claims> claims, String requestURI) {
        
        boolean acceso=false;

        if(requestURI.startsWith("/api/manejoProducto") && claims.getBody().get("tipoEmpleado").equals("JEFE") 
           || requestURI.startsWith("/api/manejoEmpleado") && claims.getBody().get("tipoEmpleado").equals("JEFE") ){
            acceso=true;
        }

        return acceso;
    }





    /*
     * notificar un error en caso de que aparezca una excepcion
     */
    private void crearRespuestaError(String mensaje, int codigoError, HttpServletResponse
            response) throws IOException {
        RespuestaDto<String> dto = new RespuestaDto<>(true, mensaje);

        response.setContentType("application/json");
        response.setStatus(codigoError);
        response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
        response.getWriter().flush();
        response.getWriter().close();
    }



    
    /*
     * verificar y extraer el token del encabezado http
     */
    private String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer "))
            return header.replace("Bearer ", "");
        return null;
        
    }



}
