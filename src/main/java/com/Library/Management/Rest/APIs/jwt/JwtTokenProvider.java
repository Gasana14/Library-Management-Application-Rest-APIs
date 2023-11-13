package com.Library.Management.Rest.APIs.jwt;

import com.Library.Management.Rest.APIs.exception.LibraryManagementException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

// This class will help us to generate JWT Token

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-millesconds}")
    private long jwtExpirationDate;



    // generate JWT Token
    public String generateJwtToken(Authentication authentication){
        String username = authentication.getName();

        // get current date
        Date currentDate = new Date();

        //Expiration Date
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        // create jwt
        String jwtToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return jwtToken;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }


    // get username from jwt token
    public String getUsername(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        return username;
    }


    // validate jwt token
    public boolean validateJwtToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        }catch (MalformedJwtException ex){
            throw new LibraryManagementException(HttpStatus.BAD_REQUEST,"Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            throw new LibraryManagementException(HttpStatus.BAD_REQUEST,"Expired JWT Token");
        }catch (UnsupportedJwtException ex){
            throw new LibraryManagementException(HttpStatus.BAD_REQUEST,"Unsupported JWT Token");
        }catch (IllegalArgumentException ex){
            throw new LibraryManagementException(HttpStatus.BAD_REQUEST,"JWT Claims string is empty");
        }

    }
}
