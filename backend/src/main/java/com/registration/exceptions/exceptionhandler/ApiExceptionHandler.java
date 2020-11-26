package com.registration.exceptions.exceptionhandler;

import com.registration.exceptions.AcessoNegadoException;
import com.registration.exceptions.NaoAutorizadoException;
import com.registration.exceptions.NegocioException;
import com.registration.exceptions.RecursoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<Object> handleNotFound(RecursoNaoEncontradoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo("Não encontrado.");
		problema.setMensagem("A aplicação não conseguiu mapear a requisição para um recurso conhecido.");
		problema.setDetalhe(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo("Regra negocial não atendida.");
		problema.setMensagem(ex.getMessage());
		problema.setDetalhe(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleExceptionGeneric(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo("Erro interno.");
		problema.setMensagem("Falha genérica na aplicação.");
		problema.setDetalhe(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(AcessoNegadoException.class)
	public ResponseEntity<Object> handleAcessoNegadoException(AcessoNegadoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo("Acesso Negado");
		problema.setMensagem(ex.getMessage());
		problema.setDetalhe(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAcessoNegadoException(AccessDeniedException ex, WebRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo("Acesso Negado");
		problema.setMensagem(ex.getMessage());
		problema.setDetalhe(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NaoAutorizadoException.class)
	public ResponseEntity<Object> handleNaoAutorizadoException(NaoAutorizadoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo("Não autorizado");
		problema.setMensagem(ex.getMessage());
		problema.setDetalhe(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Problema.Campo> campos = new ArrayList<>();
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			campos.add(new Problema.Campo(nome, mensagem));
		}
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo("Um ou mais campos estão inválidos. "
				+ "Faça o preenchimento correto e tente novamente");
		problema.setDataHora(OffsetDateTime.now());
		problema.setCampos(campos);
		
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}

	
}
