package br.com.pedidos.controle;

import java.io.IOException;
import java.text.ParseException;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.pedidos.excecoes.BadRequestException;
import br.com.pedidos.excecoes.DuplicateObjectException;

@Component
public abstract class Erros {
	@ExceptionHandler(DuplicateObjectException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	private void jaCadastrado(DuplicateObjectException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
	}
	
	@ExceptionHandler(NoResultException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	private void semResultados(NoResultException e) {	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, 
		IOException.class, HttpMediaTypeNotSupportedException.class, ParseException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private void MetodoInvalido() { }
	
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private void DadosIncorretos(BadRequestException e, HttpServletResponse response) throws IOException {
		if(e != null)
			response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private void exception(Exception e) {
		e.printStackTrace();
	}
}
