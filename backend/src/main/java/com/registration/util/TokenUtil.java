package com.registration.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.registration.model.Login;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("tokenUtil")
public class TokenUtil {

    public TokenUtil() {
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private Login login;

    public static final String GOVBR = "GOV.BR";
    public static final String SSO = "SSO";
    public static final String LEITURISTA = "LEITURISTA";

    public void validate() {
        String user = Jwts.parser()
                .setSigningKey("AUTH_Segadmin_h45h_k3Y")
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody()
                .getSubject();
        try {
            login = objectMapper.readValue(user, Login.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
