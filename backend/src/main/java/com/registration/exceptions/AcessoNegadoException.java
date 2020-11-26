package com.registration.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AcessoNegadoException extends RuntimeException {
    private String titulo;
    private Integer codigo;
    private Map<String, Object> extra;

    public AcessoNegadoException(String message) {
        this(message, null, new Integer(403), null);
    }

    public AcessoNegadoException(String message, String titulo, Integer codigo, Map<String, Object> extra) {
        super(message);
        this.titulo = titulo;
        this.codigo = codigo;
        this.extra = extra;
    }
}

