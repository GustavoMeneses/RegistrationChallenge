package com.registration.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NaoAutorizadoException extends RuntimeException {
    public NaoAutorizadoException() {
        super("Não foi possível autenticar-se usando as credenciais fornecidas.");
    }
}

