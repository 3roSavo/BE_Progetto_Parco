package savoginEros.ParkprojectBE.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.UnauthorizedException;

import java.util.Date;

@Component
public class JWTTools {

    @Value("${spring.jwt.secret}")
    private String secret;

    //METODI

    public String createToken(User user) {
        String accessToken = Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
        return accessToken;
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parse(token);
        } catch (Exception e) {
            throw new UnauthorizedException("Token scaduto o manomesso, effettua nuovamente il login");
        }
    }

    public String extractIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
