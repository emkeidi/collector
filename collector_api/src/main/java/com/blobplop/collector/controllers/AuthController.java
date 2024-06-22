package com.blobplop.collector.controllers;

import com.blobplop.collector.domain.Result;
import com.blobplop.collector.entities.AppUser;
import com.blobplop.collector.entities.Credentials;
import com.blobplop.collector.security.AppUserService;
import com.blobplop.collector.security.JwtConverter;
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

                return new ResponseEntity<>(map, HttpStatus.OK);
            }

        } catch (AuthenticationException ex) {
            System.out.println(ex);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<Map<String, String>> refreshToken(@AuthenticationPrincipal AppUser appUser) {
        String jwtToken = converter.getTokenFromUser(appUser);

        HashMap<String, String> map = new HashMap<>();
        map.put("jwt_token", jwtToken);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/create_account")
    public ResponseEntity<?> createAccount(@RequestBody Credentials credentials) {

        Result<AppUser> result =
                appUserService.create(credentials.getUsername(),
                        credentials.getPassword(), credentials.getEmail());

        // unhappy path...
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(),
                    HttpStatus.BAD_REQUEST);
        }

        // happy path...
        HashMap<String, Long> map = new HashMap<>();
        map.put("appUserId", result.getPayload().getAppUserId());

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
}
