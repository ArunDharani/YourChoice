package com.ecommerce.YourChoice.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.Key;
import java.util.Date;


public class JWTService {

    private static final Logger logger = LoggerFactory.getLogger(JWTService.class);

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    public String getJWTFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization Header : {} ",bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return  null;
    }

    public String generateTokenFromUser(UserDetails userDetails) {
        String userName = userDetails.getUsername();
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }


    public String getUserNameFromJWTToken(String token) {
        return Jwts.parserBuilder()             // Correct for 0.11.x
                .setSigningKey(key())           // Replaces verifyWith
                .build()
                .parseClaimsJws(token)          // Replaces parseSignedClaims
                .getBody()                      // Replaces getPayload
                .getSubject();             // Correct spelling: getSubject()
    }

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {

            System.out.println("Validate");
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);

            return true;
        } catch (MalformedJwtException exception) {
            logger.error("Malformed exception");
        }

        return false;
    }


}
