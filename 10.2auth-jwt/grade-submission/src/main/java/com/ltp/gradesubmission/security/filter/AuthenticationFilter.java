package com.ltp.gradesubmission.security.filter;


import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.security.SecurityConstants;
import com.ltp.gradesubmission.security.manager.CustomAuthenticationManager;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    // UserService userService;
    private CustomAuthenticationManager customAuthenticationManager;;

    //  @Override
    // public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    //         throws IOException, ServletException {
    //     chain.doFilter(request, response);
    // }

    //  /login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        try {
            
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            return customAuthenticationManager.authenticate(authentication);

        } catch (IOException o) {
            throw new RuntimeException();
        }
    }

    // send badck JWT
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
            String token = JWT.create() // type
                .withSubject(authResult.getName()) //payload
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY)); // algo security key
                
            response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER + token);
    }

    // Authorization: Basic Username:Password
    // Authorization: Bear JWT Token
    
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(failed.getMessage()); // incorrect message
            response.getWriter().flush();
        
    }
    
}
