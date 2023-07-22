package com.pathi.blog.security;

import com.pathi.blog.exception.BlogApiExceptionHandler;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Value("${jwt.expiry-milliseconds}")
    private Long jwtExpiry;

    //generate Token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + jwtExpiry);
        return Jwts.builder()
                .setAudience(username)
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .setSubject(username)
                .signWith(key())
                .compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecretKey)
        );
    }
    // get Username from claims
    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build().parseClaimsJws(token).getBody();
        return claims.getAudience();
    }


    // isvalid Token
    public Boolean isValidateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        }
        catch (MalformedJwtException e){
            throw new BlogApiExceptionHandler(HttpStatus.BAD_REQUEST,"Invalid Jwt Token");
        }
        catch (ExpiredJwtException e){
            throw new BlogApiExceptionHandler(HttpStatus.BAD_REQUEST,"Expired Jwt Token");
        }
        catch (UnsupportedJwtException ex){
            throw new BlogApiExceptionHandler(HttpStatus.BAD_REQUEST,"Unsupported Jwt token");
        }
        catch (IllegalArgumentException ex){
            throw new BlogApiExceptionHandler(HttpStatus.BAD_REQUEST,"Jwt claims string is empty");
        }
    }
}
