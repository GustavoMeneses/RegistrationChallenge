package com.registration.service;

import com.google.gson.Gson;
import com.registration.exceptions.NaoAutorizadoException;
import com.registration.filter.AuthUserResult;
import com.registration.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private BCryptPasswordEncoder encoder;

    @Autowired
    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String loginCredentials) throws UsernameNotFoundException {
        Gson gson = new Gson();
        Login login = gson.fromJson(loginCredentials, Login.class);

        String sha1 = "";

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(login.getPassword().getBytes("utf8"));
            sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Login existente = userService.recuperar(login.getLogin(), sha1);

        if (existente == null) {
            throw new NaoAutorizadoException();
        }

        return new AuthUserResult(
                existente,
                login.getLogin(),
                encoder.encode(login.getPassword()),
                this.getAuthorities(existente.getProfile().getProfile())
        );
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(String perfil) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(perfil));
        return authorities;
    }
}
