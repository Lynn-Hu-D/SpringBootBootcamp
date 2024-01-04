package com.ltp.gradesubmission.security.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ltp.gradesubmission.security.SecurityConstants;

public class JWTAuthorizationFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // verify the JWT token
        String header = request.getHeader("Authorization"); // get the bearer token (Bearer JWT)

        if (header == null || !header.startsWith(SecurityConstants.BEARER)){
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.replace(SecurityConstants.BEARER, ""); // get the token

        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
        .build()
        .verify(token)
        .getSubject();

        // use the right constructor new UsernamePasswordAuthenticationToken(user, null, Arrays.asList())
        // null pointer exception
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
    
}
