package com.blobplop.collector.security;

import com.blobplop.collector.entities.AppUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtRequestFilter extends BasicAuthenticationFilter {
    private final JwtConverter converter;

    Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    public JwtRequestFilter(AuthenticationManager authenticationManager,
                            JwtConverter converter) {
        super(authenticationManager); // 1. Must satisfy the super class.
        this.converter = converter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException,
                                                              ServletException {

        // 2. Read the Authorization value from the request.
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {

            // 3. The value looks okay, confirm it with JwtConverter.
            AppUser user = converter.getUserFromToken(authorization);
            if (user == null) {
                response.setStatus(403); // Forbidden
            } else {

                // 4. Confirmed. Set auth for this single request.
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(
                                user, null, user.getAuthorities());


                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }

        // 5. Keep the chain going.
        chain.doFilter(request, response);
    }
}
