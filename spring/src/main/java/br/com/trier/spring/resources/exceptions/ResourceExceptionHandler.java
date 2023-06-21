package br.com.trier.spring.resources.exceptions;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.trier.spring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring.services.exceptions.ViolacaoIntegridade;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjetoNaoEncontrado.class)
	public ResponseEntity<StandardError> objetoNaoEncontrado(ObjetoNaoEncontrado ex, HttpServletRequest request){
		StandardError erro = new StandardError(ZonedDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(ViolacaoIntegridade.class)
	public ResponseEntity<StandardError> violacaoIntegridade(ViolacaoIntegridade ex, HttpServletRequest request){
		StandardError erro = new StandardError(ZonedDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
}
