package com.blobplop.collector.security;

import com.blobplop.collector.entities.AppUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;


public class JwtConverter {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final String ISSUER = "com.blobplop.collector";
    private final int EXPIRATION_MINUTES = 60;
    private final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

    // Take an instance of `AppUser` as a parameter, instead of `UserDetails`
    public String getTokenFromUser(AppUser user) {

        String authorities = user.getAuthorities().stream()
                .map(i -> i.getAuthority())
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(user.getUsername())
                // new... embed the `appUserId` in the JWT as a claim
                .claim("app_user_id", user.getAppUserId())
                .claim("authorities", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(key)
                .compact();
    }

    // Return an instance of `AppUser`
    public AppUser getUserFromToken(String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer(ISSUER)
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.substring(7));

            String username = jws.getBody().getSubject();
            int appUserId = (int) jws.getBody().get("app_user_id");
            String authStr = (String) jws.getBody().get("authorities");

            return new AppUser(appUserId, username, null, true,
                    Arrays.asList(authStr.split(",")));

        } catch (JwtException e) {
            System.out.println(e);
        }

        return null;
    }
}
