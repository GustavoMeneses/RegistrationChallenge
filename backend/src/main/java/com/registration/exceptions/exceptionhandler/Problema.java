package com.registration.exceptions.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@Data
public class Problema {

	private Integer status;
	private OffsetDateTime dataHora;
	private String titulo;
	private String mensagem;
	private String detalhe;
	private List<Campo> campos;

	@Data
	@AllArgsConstructor
	public static class Campo {
		private String nome;
		private String mensagem;

	}
}
