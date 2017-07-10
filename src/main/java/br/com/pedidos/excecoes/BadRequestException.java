package br.com.pedidos.excecoes;

public class BadRequestException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public BadRequestException() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public BadRequestException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}
}
