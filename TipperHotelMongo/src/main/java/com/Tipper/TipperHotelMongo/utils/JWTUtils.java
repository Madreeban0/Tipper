package com.Tipper.TipperHotelMongo.utils;


import com.Tipper.TipperHotelMongo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTUtils {
    private static final long  EXPIRATION_TIME = 1000*60*24*7;


    private final SecretKey Key;
    private UserDetails userDetails;


    public JWTUtils() {
        String secretString = "20385239958089213484935";
        byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
        .signWith(this.Key)
        .compact();
    }



    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims , T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parserBuilder().setSigningKey(Key).build().parseClaimsJws(token).getBody());
    }

    public boolean isValidToken(String token){
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractClaims(token, Claims :: getExpiration).before(new Date());
    }

    public Object generateToken(User user) {
        return null;
    }
}