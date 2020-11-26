package com.registration.configuration;


import com.registration.filter.AuthFilter;
import com.registration.filter.AuthorizationFilter;
import com.registration.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService detailsService;
    private BCryptPasswordEncoder encoder;

    @Value("${auth.time.session:20}")
    public Integer time;

    @Value("${auth.jwtsecret}")
    private String jwtSecret;

    public static  Integer tempoSessao;

    public static final String[] GET_AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui",
            "/webjars/**"
    };

    @Autowired
    public void setDetailsService(UserDetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @Autowired
    public void setEncoder(@Lazy BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public static final String[] POST_AUTH_WHITELIST = {
//            "/**",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        tempoSessao = this.time;
        AuthFilter authFilter = new AuthFilter(authenticationManager());
        authFilter.jwtSecret = this.jwtSecret;
        http.cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, GET_AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST, POST_AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.PUT, POST_AUTH_WHITELIST).permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .addFilter(authFilter)
                .addFilter(new AuthorizationFilter(authenticationManager()));

        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService).passwordEncoder(encoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();

        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        cors.addAllowedOrigin("*");
        cors.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);

        return source;
    }
}
