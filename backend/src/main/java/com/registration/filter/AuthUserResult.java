package com.registration.filter;

import com.registration.model.Login;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthUserResult extends User {

    private Login login;

    public AuthUserResult(
            Login login,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);
        this.login = login;
    }

    public Login getLogin() {
        login.setPassword(null);
        return login;
    }
}


