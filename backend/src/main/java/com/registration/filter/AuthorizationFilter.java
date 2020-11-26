package com.registration.filter;

import com.registration.model.Login;
import com.registration.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Value("${auth.jwtsecret}")
    private String jwtSecret;

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpRequest = request;
        if (this.objectMapper == null) {
            ServletContext context = httpRequest.getSession().getServletContext();
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, context);
        }

        UsernamePasswordAuthenticationToken authenticationToken = validateToken(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        if (authenticationToken == null) {
            String error = "{ \"message\": \"Sessão expirada\", \"codeError\": \"" + HttpServletResponse.SC_UNAUTHORIZED + "\" }";
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(error);
            response.getWriter().flush();
            response.getWriter().close();
            return;
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken validateToken (HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null) {
                return null;
            }
            String user = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(authHeader.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();
            if (user == null) {
                return null;
            }
            Login login = objectMapper.readValue(user, Login.class);
            tokenUtil.setLogin(login);
            List<GrantedAuthority> authorities = new ArrayList<>();
            return new UsernamePasswordAuthenticationToken(login, null, authorities);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            return null;
        } catch (Exception e)  {
            e.printStackTrace();
            return null;
        }
    }
}
