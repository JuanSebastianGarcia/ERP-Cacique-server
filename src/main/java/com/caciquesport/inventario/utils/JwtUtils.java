package com.caciquesport.inventario.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

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









}
