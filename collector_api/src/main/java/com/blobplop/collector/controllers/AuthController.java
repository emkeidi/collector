package com.blobplop.collector.controllers;

import com.blobplop.collector.domain.Result;
import com.blobplop.collector.entities.AppUser;
import com.blobplop.collector.entities.Credentials;
import com.blobplop.collector.security.AppUserService;
import com.blobplop.collector.security.JwtConverter;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@ConditionalOnWebApplication
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtConverter converter;
    private final AppUserService appUserService;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);


    public AuthController(AuthenticationManager authenticationManager,
                          JwtConverter converter,
                          AppUserService appUserService) {
        this.authenticationManager = authenticationManager;
        this.converter = converter;
        this.appUserService = appUserService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody Credentials credentials) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());

        try {
            Authentication authentication =
                    authenticationManager.authenticate(authToken);

            if (authentication.isAuthenticated()) {
                String jwtToken =
                        converter.getTokenFromUser((AppUser) authentication.getPrincipal());

                HashMap<String, String> map = new HashMap<>();
                map.put("jwt_token", jwtToken);

                logger.info("Authentication successful for user: {}", credentials.getUsername());
                return new ResponseEntity<>(map, HttpStatus.OK);
            }

        } catch (AuthenticationException ex) {
            logger.error("Authentication failed for user: {}. Reason: {}", credentials.getUsername(), ex.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @PostMapping("/refresh_token")
    public ResponseEntity<Map<String, String>> refreshToken(@AuthenticationPrincipal AppUser appUser) {
        if (appUser == null) {
            logger.error("Authorization header is missing or the JWT token is not valid.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            String jwtToken = converter.getTokenFromUser(appUser);

            HashMap<String, String> map = new HashMap<>();
            map.put("jwt_token", jwtToken);

            logger.info("Token refreshed for user: {}", appUser.getUsername());
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (JwtException ex) {
            logger.error("JWT validity cannot be asserted and should not be trusted for user: {}. Reason: {}", appUser.getUsername(), ex.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/create_account")
    public ResponseEntity<?> createAccount(@RequestBody Credentials credentials) {

        Result<AppUser> result =
                appUserService.create(credentials.getUsername(),
                        credentials.getPassword(), credentials.getEmail());

        // unhappy path...
        if (!result.isSuccess()) {
            logger.error("Failed to create account for user: {}. Cause: {}",
                    credentials.getUsername(), result.getMessages());
            return new ResponseEntity<>(result.getMessages(),
                    HttpStatus.BAD_REQUEST);
        }

        // happy path...
        HashMap<String, Long> map = new HashMap<>();
        map.put("appUserId", result.getPayload().getAppUserId());

        logger.info("Account created for user: {}", credentials.getUsername());
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
}
