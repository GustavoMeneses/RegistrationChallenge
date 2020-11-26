package com.registration.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.registration.model.Login;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.EntityConverter;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


public class AuthFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    public String jwtSecret;

    @Autowired
    EntityConverter entityConverter;

    public AuthFilter(AuthenticationManager authenticationManager) {
        this.setUsernameParameter("login");
        this.setPasswordParameter("password");
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Login login = new ObjectMapper().readValue(request.getInputStream(), Login.class);
            Gson gson = new Gson();

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            gson.toJson(login),
                            login.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            System.out.println(e);
            throw new BadCredentialsException(e.getMessage());
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Login login = ((AuthUserResult) authResult.getPrincipal()).getLogin();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        Date expTime = calendar.getTime();

        String token = Jwts.builder()
                .setSubject(mapper.writeValueAsString(login))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .setExpiration(expTime)
                .compact();

        response.addHeader("Authorization", "Bearer " + token);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        response.getWriter().write(mapper.writeValueAsString(login));
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        String error = "{ \"mensagem\": \"" + failed.getMessage() + "\",  \"status\": \"" +
                HttpServletResponse.SC_UNAUTHORIZED + "\" }";
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(error);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
